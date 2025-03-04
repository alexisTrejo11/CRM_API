package at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses;

import java.math.BigDecimal;

public record Budget(BigDecimal totalBudget, BigDecimal costToDate) {
    public Budget {
        if (totalBudget != null && totalBudget.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El presupuesto no puede ser negativo");
        }
        costToDate = costToDate == null ? BigDecimal.ZERO : costToDate;
    }

    public Budget addCost(BigDecimal amount) {
        return new Budget(totalBudget, costToDate.add(amount));
    }

    public boolean exceedsBudget() {
        return totalBudget != null && costToDate.compareTo(totalBudget) > 0;
    }
}