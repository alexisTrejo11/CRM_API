package at.backend.MarketingCompany.marketing.attribution.domain;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.AttributionModel;
import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.*;
        import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CampaignAttribution {
    private final AttributionId id;
    private final DealId dealId;
    private final CampaignId campaignId;
    private final AttributionModel model;
    private final AttributionPercentage percentage;
    private final AttributedRevenue revenue;
    private final TouchTimeline timeline;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private CampaignAttribution(
            AttributionId id,
            DealId dealId,
            CampaignId campaignId,
            AttributionModel model,
            AttributionPercentage percentage,
            AttributedRevenue revenue,
            TouchTimeline timeline,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.dealId = dealId;
        this.campaignId = campaignId;
        this.model = model;
        this.percentage = percentage;
        this.revenue = revenue;
        this.timeline = timeline;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CampaignAttribution create(
            DealId dealId,
            CampaignId campaignId,
            AttributionModel model,
            TouchTimeline timeline
    ) {
        AttributionPercentage initialPercentage = calculatePercentageForModel(model);
        return new CampaignAttribution(
                AttributionId.of(generateId()),
                dealId,
                campaignId,
                model,
                initialPercentage,
                new AttributedRevenue(BigDecimal.ZERO),
                timeline,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private static UUID generateId() {
        return UUID.randomUUID();
    }

    public CampaignAttribution addTouch(LocalDateTime touchTime) {
        return new CampaignAttribution(
                this.id,
                this.dealId,
                this.campaignId,
                this.model,
                this.percentage,
                this.revenue,
                this.timeline.addTouch(touchTime),
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public CampaignAttribution recalculateForModel(AttributionModel newModel) {
        return new CampaignAttribution(
                this.id,
                this.dealId,
                this.campaignId,
                newModel,
                calculatePercentageForModel(newModel),
                this.revenue,
                this.timeline,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public static AttributionPercentage calculatePercentageForModel(AttributionModel model) {
        return switch (model) {
            case FIRST_TOUCH -> new AttributionPercentage(BigDecimal.valueOf(100));
            case LAST_TOUCH -> new AttributionPercentage(BigDecimal.valueOf(50));
            default -> throw new UnsupportedOperationException("Calculation not implemented for " + model);
        };
    }
}
