package at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs;


import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CampaignActivityDTO {

    private UUID id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Activity type cannot be null")
    private ActivityType activityType;

    @FutureOrPresent(message = "Planned start date must be in the present or future")
    private LocalDateTime plannedStartDate;

    @FutureOrPresent(message = "Planned end date must be in the present or future")
    private LocalDateTime plannedEndDate;

    private LocalDateTime actualStartDate;

    private LocalDateTime actualEndDate;

    @NotNull(message = "Status cannot be null")
    private ActivityStatus status;

    @DecimalMin(value = "0.0", inclusive = true, message = "Planned cost must be greater than or equal to zero")
    private BigDecimal plannedCost;

    @DecimalMin(value = "0.0", inclusive = true, message = "Actual cost must be greater than or equal to zero")
    private BigDecimal actualCost;

    private UUID assignedTo;

    private UUID campaignId;


    @Size(max = 500, message = "Success criteria cannot exceed 500 characters")
    private String successCriteria;

    @Size(max = 500, message = "Target audience cannot exceed 500 characters")
    private String targetAudience;

    private String deliveryChannel;
}