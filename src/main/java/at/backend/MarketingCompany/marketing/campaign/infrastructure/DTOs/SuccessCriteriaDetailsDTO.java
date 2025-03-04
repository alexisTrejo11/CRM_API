package at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Map;
import java.util.Objects;

public record SuccessCriteriaDetailsDTO(
        @NotBlank(message = "Description is required")
        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Metrics cannot be null")
        @Size(min = 1, message = "At least one metric is required")
        Map<@NotBlank(message = "Metric name cannot be blank") String,
                @NotNull(message = "Metric value cannot be null") Double> metrics
) {
    public SuccessCriteriaDetailsDTO {
        Objects.requireNonNull(metrics, "Metrics cannot be null");
        metrics.forEach((key, value) -> {
            if (value < 0) {
                throw new IllegalArgumentException("Metric value cannot be negative");
            }
        });
    }
}
