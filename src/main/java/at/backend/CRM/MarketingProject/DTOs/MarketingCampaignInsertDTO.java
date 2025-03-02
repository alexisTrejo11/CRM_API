package at.backend.CRM.MarketingProject.DTOs;

import at.backend.CRM.MarketingProject.Utils.Enums.CampaignType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class MarketingCampaignInsertDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    private LocalDate endDate;

    @DecimalMin(value = "0.0", inclusive = true, message = "Budget must be greater than or equal to zero")
    private BigDecimal budget;

    @Size(max = 500, message = "Target audience cannot exceed 500 characters")
    private String targetAudience;

    @Size(max = 500, message = "Success criteria cannot exceed 500 characters")
    private String successCriteria;

    private Map<String, Double> targets = new HashMap<>();

    @NotNull(message = "Type cannot be null")
    private CampaignType type;
}