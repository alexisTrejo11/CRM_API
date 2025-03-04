package at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses;

import java.util.Map;
import java.util.Objects;

public record SuccessCriteria(String description, Map<String, Double> metrics) {
    public SuccessCriteria(String description, Map<String, Double> metrics) {
        this.description = validateDescription(description);
        this.metrics = validateMetrics(metrics);
    }

    private String validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("The success criteria description cannot be empty");
        }
        if (description.length() > 500) {
            throw new IllegalArgumentException("The description cannot exceed 500 characters");
        }
        return description.trim();
    }

    private Map<String, Double> validateMetrics(Map<String, Double> metrics) {
        Objects.requireNonNull(metrics, "Metrics cannot be null");
        if (metrics.isEmpty()) {
            throw new IllegalArgumentException("At least one metric must be provided");
        }
        for (Map.Entry<String, Double> entry : metrics.entrySet()) {
            if (entry.getKey() == null || entry.getKey().trim().isEmpty()) {
                throw new IllegalArgumentException("The metric name cannot be empty");
            }
            if (entry.getValue() == null || entry.getValue() < 0) {
                throw new IllegalArgumentException("The metric value cannot be negative");
            }
        }
        return Map.copyOf(metrics);
    }

    @Override
    public Map<String, Double> metrics() {
        return Map.copyOf(metrics);
    }

    public boolean isMet(Map<String, Double> actualMetrics) {
        for (Map.Entry<String, Double> entry : metrics.entrySet()) {
            String metricName = entry.getKey();
            Double targetValue = entry.getValue();
            Double actualValue = actualMetrics.get(metricName);

            if (actualValue == null || actualValue < targetValue) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuccessCriteria that = (SuccessCriteria) o;
        return description.equals(that.description) &&
                metrics.equals(that.metrics);
    }

    @Override
    public String toString() {
        return "SuccessCriteria{" +
                "description='" + description + '\'' +
                ", metrics=" + metrics +
                '}';
    }
}
