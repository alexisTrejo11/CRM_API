package at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers;

import java.math.BigDecimal;

public record AttributionPercentage(BigDecimal value) {
    public AttributionPercentage {
        if (value == null) {
            throw new IllegalArgumentException("Percentage cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Percentage must be between 0-100");
        }
    }
}
