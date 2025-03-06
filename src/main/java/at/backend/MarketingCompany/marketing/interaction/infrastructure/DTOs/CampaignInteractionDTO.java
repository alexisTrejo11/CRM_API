package at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MarketingInteractionType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignInteractionDTO {
    private UUID id;

    @NotNull(message = "Campaign ID cannot be null")
    private UUID campaignId;

    @NotNull(message = "CustomerModel ID cannot be null")
    private UUID customerId;

    @NotNull(message = "Interaction type cannot be null")
    private MarketingInteractionType interactionType;

    @NotNull(message = "Interaction date cannot be null")
    private LocalDateTime interactionDate;

    private String sourceChannel;

    private String sourceMedium;

    private String sourceCampaign;

    private String deviceType;

    private String ipAddress;

    private String geoLocation;

    private Map<String, String> properties;

    @Size(max = 1000, message = "Details cannot exceed 1000 characters")
    private String details;

    private UUID resultedDealId;

    @Min(value = 0, message = "Conversion value must be greater than or equal to zero")
    private Double conversionValue;
}
