package at.backend.MarketingCompany.marketing.activity.domain.HelperClasses;

import java.math.BigDecimal;

public record ActivityCost(
        BigDecimal plannedCost,
        BigDecimal actualCost
) {
    public ActivityCost {
        if (plannedCost == null || plannedCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Planned cost must be positive");
        }
        actualCost = actualCost != null ? actualCost : BigDecimal.ZERO;
    }

    public ActivityCost withActualCost(BigDecimal amount) {
        return new ActivityCost(plannedCost, amount);
    }

    public boolean hasCostOverrun() {
        return actualCost.compareTo(plannedCost) > 0;
    }
}
