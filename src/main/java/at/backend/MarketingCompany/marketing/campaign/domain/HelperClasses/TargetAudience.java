package at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses;

import lombok.Builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
public record TargetAudience(String description, Set<String> demographics, Set<String> geographicLocations,
                             Set<String> interests) {
    public TargetAudience(String description, Set<String> demographics, Set<String> geographicLocations, Set<String> interests) {
        this.description = validateDescription(description);
        this.demographics = Objects.requireNonNull(demographics, "Demographic data cannot be null");
        this.geographicLocations = Objects.requireNonNull(geographicLocations, "Geographic locations cannot be null");
        this.interests = Objects.requireNonNull(interests, "Interests cannot be null");

        validateDemographics();
        validateGeographicLocations();
        validateInterests();
    }

    private String validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("The audience description cannot be empty");
        }
        if (description.length() > 500) {
            throw new IllegalArgumentException("The description cannot exceed 500 characters");
        }
        return description.trim();
    }

    private void validateDemographics() {
        if (demographics.isEmpty()) {
            throw new IllegalArgumentException("At least one demographic data must be provided");
        }
    }

    private void validateGeographicLocations() {
        if (geographicLocations.isEmpty()) {
            throw new IllegalArgumentException("At least one geographic location must be provided");
        }
    }

    private void validateInterests() {
        if (interests.isEmpty()) {
            throw new IllegalArgumentException("At least one interest must be provided");
        }
    }

    @Override
    public Set<String> demographics() {
        return Set.copyOf(demographics);
    }

    @Override
    public Set<String> geographicLocations() {
        return Set.copyOf(geographicLocations);
    }

    @Override
    public Set<String> interests() {
        return Set.copyOf(interests);
    }

    public TargetAudience combineWith(TargetAudience other) {
        Set<String> combinedDemographics = new HashSet<>(this.demographics);
        combinedDemographics.addAll(other.demographics);

        Set<String> combinedGeographicLocations = new HashSet<>(this.geographicLocations);
        combinedGeographicLocations.addAll(other.geographicLocations);

        Set<String> combinedInterests = new HashSet<>(this.interests);
        combinedInterests.addAll(other.interests);

        return new TargetAudience(
                this.description + " and " + other.description,
                combinedDemographics,
                combinedGeographicLocations,
                combinedInterests
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetAudience that = (TargetAudience) o;
        return description.equals(that.description) &&
                demographics.equals(that.demographics) &&
                geographicLocations.equals(that.geographicLocations) &&
                interests.equals(that.interests);
    }

    @Override
    public String toString() {
        return "TargetAudience{" +
                "description='" + description + '\'' +
                ", demographics=" + demographics +
                ", geographicLocations=" + geographicLocations +
                ", interests=" + interests +
                '}';
    }
}
