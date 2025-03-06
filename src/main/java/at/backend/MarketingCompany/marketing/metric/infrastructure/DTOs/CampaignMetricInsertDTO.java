package at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MetricType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignMetricInsertDTO {

    @NotNull(message = "Campaign ID cannot be null")
    private UUID campaignId;

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

    @Size(max = 1000, message = "Calculation formula cannot exceed 1000 characters")
    private String calculationFormula;

    @Size(max = 500, message = "Data source cannot exceed 500 characters")
    private String dataSource;

    private boolean automated;
}
