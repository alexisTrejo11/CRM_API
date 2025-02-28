package at.backend.CRM.MarketingProject.DTOs;

import at.backend.CRM.MarketingProject.Models.Utils.MetricType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CampaignMetricDTO {

    private Long id;

    @NotNull(message = "Campaign ID cannot be null")
    private Long campaignId;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Metric type cannot be null")
    private MetricType type;

    @DecimalMin(value = "0.0", inclusive = true, message = "Value must be greater than or equal to zero")
    private BigDecimal value;

    @DecimalMin(value = "0.0", inclusive = true, message = "Target value must be greater than or equal to zero")
    private BigDecimal targetValue;

    private String measurementUnit;

    private LocalDateTime lastCalculated;

    @Size(max = 1000, message = "Calculation formula cannot exceed 1000 characters")
    private String calculationFormula;

    @Size(max = 500, message = "Data source cannot exceed 500 characters")
    private String dataSource;

    private boolean automated;
}