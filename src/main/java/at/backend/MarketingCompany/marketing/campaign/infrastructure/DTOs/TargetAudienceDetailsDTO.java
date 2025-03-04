package at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.Set;

public record TargetAudienceDetailsDTO(
        @NotBlank(message = "Description is required")
        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Demographics cannot be null")
        @Size(min = 1, message = "At least one demographic is required")
        Set<@NotBlank(message = "Demographic cannot be blank") String> demographics,

        @NotNull(message = "Geographic locations cannot be null")
        @Size(min = 1, message = "At least one geographic location is required")
        Set<@NotBlank(message = "Geographic location cannot be blank") String> geographicLocations,

        @NotNull(message = "Interests cannot be null")
        @Size(min = 1, message = "At least one interest is required")
        Set<@NotBlank(message = "Interest cannot be blank") String> interests
) {
    public TargetAudienceDetailsDTO {
        Objects.requireNonNull(demographics, "Demographics cannot be null");
        Objects.requireNonNull(geographicLocations, "Geographic locations cannot be null");
        Objects.requireNonNull(interests, "Interests cannot be null");
    }
}
