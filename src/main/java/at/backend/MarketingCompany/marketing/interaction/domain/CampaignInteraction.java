package at.backend.MarketingCompany.marketing.interaction.domain;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MarketingInteractionType;
import at.backend.MarketingCompany.crm.deal.domain.Deal;
import at.backend.MarketingCompany.customer.api.repository.CustomerModel;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class CampaignInteraction {

    private final CampaignInteractionId id;
    private final MarketingCampaign campaign;
    private final CustomerModel customerModel;
    private final MarketingInteractionType interactionType;
    private LocalDateTime interactionDate;
    private InteractionSource source;
    private final DeviceInfo deviceInfo;
    private final GeoLocation geoLocation;
    private final Map<String, String> properties;
    private String details;
    private Deal resultedDeal;
    private Double conversionValue;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CampaignInteraction(
            CampaignInteractionId id,
            MarketingCampaign campaign,
            CustomerModel customerModel,
            MarketingInteractionType interactionType,
            LocalDateTime interactionDate,
            InteractionSource source,
            DeviceInfo deviceInfo,
            GeoLocation geoLocation,
            Map<String, String> properties,
            LocalDateTime createdAt
    ) {
        //validateInteractionDate(intactionDate);
        validateCampaignStatus(campaign);
        validateCustomer(customerModel);

        this.id = id;
        this.campaign = campaign;
        this.customerModel = customerModel;
        this.interactionType = interactionType;
        this.interactionDate = interactionDate;
        this.source = source;
        this.deviceInfo = deviceInfo;
        this.geoLocation = geoLocation;
        this.properties = new HashMap<>(properties);
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public void setConversion(Deal deal, Double value) {
        if (deal == null && value != null) {
            throw new IllegalArgumentException("Deal must be provided when setting conversion value");
        }
        if (value != null && value <= 0) {
            throw new IllegalArgumentException("Conversion value must be positive");
        }
        this.resultedDeal = deal;
        this.conversionValue = value;
        this.updatedAt = LocalDateTime.now();
    }

    public void addProperty(String key, String value) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Property key cannot be empty");
        }
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Property value cannot be empty");
        }
        this.properties.put(key, value);
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isConversion() {
        return resultedDeal != null && conversionValue != null;
    }

    private void validateInteractionDate(LocalDateTime date) {
        if (date.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Interaction date cannot be in the future");
        }
    }

    private void validateCampaignStatus(MarketingCampaign campaign) {
        if (!campaign.isActive()) {
            throw new IllegalStateException("Cannot create interaction for inactive campaign");
        }
    }

    private void validateCustomer(CustomerModel customerModel) {
        if (customerModel.isBlocked()) {
            throw new IllegalStateException("Cannot create interaction for blocked customerModel");
        }
    }

    public Double calculateAttributionValue() {
        if (!isConversion()) return 0.0;

        double campaignROI = campaign.calculateROI();
        double attributionWeight = Math.max(0, campaignROI);

        return conversionValue * attributionWeight;
    }

    @Getter
    @Builder
    public static class InteractionSource {
        private final String channel;
        private final String medium;
        private final String campaignName;
    }

    @Getter
    @Builder
    public static class DeviceInfo {
        private final String deviceType;
        private final String ipAddress;
    }

    @Getter
    @Builder
    public static class GeoLocation {
        private final String country;
        private final String region;
        private final String city;
    }
}
