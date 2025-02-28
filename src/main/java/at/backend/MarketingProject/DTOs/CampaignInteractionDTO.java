package at.backend.MarketingProject.DTOs;

import at.backend.CRM.Utils.enums.InteractionType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class CampaignInteractionDTO {

    private Long id;

    @NotNull(message = "Campaign ID cannot be null")
    private Long campaignId;

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotNull(message = "Interaction type cannot be null")
    private InteractionType interactionType;

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

    private Long resultedDealId;

    @Min(value = 0, message = "Conversion value must be greater than or equal to zero")
    private Double conversionValue;
}
