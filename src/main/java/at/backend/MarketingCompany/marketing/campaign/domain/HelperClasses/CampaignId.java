package at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses;

import java.util.UUID;

public record CampaignId(UUID value) {
    public static CampaignId generate() {
        return new CampaignId(UUID.randomUUID());
    }
}
