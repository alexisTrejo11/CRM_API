package at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses;

import java.time.LocalDate;
import java.util.Objects;

public record CampaignPeriod(LocalDate startDate, LocalDate endDate) {
    public CampaignPeriod {
        Objects.requireNonNull(startDate, "The start date is required");

        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("The end date cannot be earlier than the start date");
        }
    }
}
