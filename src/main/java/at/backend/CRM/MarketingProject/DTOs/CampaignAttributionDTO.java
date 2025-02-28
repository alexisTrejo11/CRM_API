package at.backend.CRM.MarketingProject.DTOs;

import at.backend.CRM.MarketingProject.Models.Utils.AttributionModel;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CampaignAttributionDTO {

    private Long id;

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

    private LocalDateTime firstTouchDate;

    private LocalDateTime lastTouchDate;

    @Min(value = 0, message = "Touch count must be greater than or equal to zero")
    private Integer touchCount;
}
