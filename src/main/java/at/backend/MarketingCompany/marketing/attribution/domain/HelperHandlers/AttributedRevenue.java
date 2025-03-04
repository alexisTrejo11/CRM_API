package at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers;

import java.math.BigDecimal;

public record AttributedRevenue(BigDecimal value) {
    public AttributedRevenue {
        if (value == null) {
            throw new IllegalArgumentException("Revenue cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Revenue cannot be negative");
        }
    }
}
