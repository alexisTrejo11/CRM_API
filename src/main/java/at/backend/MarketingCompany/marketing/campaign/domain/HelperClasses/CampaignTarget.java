package at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses;

public record CampaignTarget(String metricName, double targetValue) {
    public CampaignTarget {
        if (metricName == null || metricName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la métrica es requerido");
        }
        if (targetValue <= 0) {
            throw new IllegalArgumentException("El valor objetivo debe ser positivo");
        }
    }
}