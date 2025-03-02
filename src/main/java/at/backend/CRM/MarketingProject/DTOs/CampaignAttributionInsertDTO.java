package at.backend.CRM.MarketingProject.DTOs;

import at.backend.CRM.MarketingProject.Utils.Enums.AttributionModel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignAttributionInsertDTO {

    @NotNull(message = "Deal ID cannot be null")
    private Long dealId;

    @NotNull(message = "Campaign ID cannot be null")
    private Long campaignId;

    @NotNull(message = "Attribution model cannot be null")
    private AttributionModel attributionModel;

    @DecimalMin(value = "0.0", message = "Attribution percentage must be between 0 and 100")
    @DecimalMax(value = "100.0", message = "Attribution percentage must be between 0 and 100")
    private BigDecimal attributionPercentage;

    @DecimalMin(value = "0.0", message = "Attributed revenue must be greater than or equal to zero")
    private BigDecimal attributedRevenue;

    @Min(value = 0, message = "Touch count must be greater than or equal to zero")
    private Integer touchCount;
}
