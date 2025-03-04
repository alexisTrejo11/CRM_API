package at.backend.MarketingCompany.marketing.activity.domain.HelperClasses;

import java.util.UUID;

public record ActivityId(UUID value) {
    public static ActivityId generate() {
        return new ActivityId(UUID.randomUUID());
    }
}
