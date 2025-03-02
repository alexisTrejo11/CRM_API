package at.backend.CRM.MarketingProject.DTOs;

import at.backend.CRM.MarketingProject.Utils.Enums.ActivityType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignActivityInsertDTO {

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

    @DecimalMin(value = "0.0", inclusive = true, message = "Planned cost must be greater than or equal to zero")
    private BigDecimal plannedCost;

    @Size(max = 500, message = "Success criteria cannot exceed 500 characters")
    private String successCriteria;

    @Size(max = 500, message = "Target audience cannot exceed 500 characters")
    private String targetAudience;

    private String deliveryChannel;

    @NotNull(message = "campaign_id is obligatory")
    @Positive(message = "campaign_id must be positive")
    private Long campaignId;
}
