package at.backend.MarketingCompany.marketing.metric.domain;

import java.util.UUID;

public record CampaignMetricId(UUID value) {
    public static CampaignMetricId of(UUID id) {
        return new CampaignMetricId(id);
    }

    public static CampaignMetricId generate() {
        return new CampaignMetricId(UUID.randomUUID());
    }
}
