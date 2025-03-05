package at.backend.MarketingCompany.marketing.interaction.domain;

import java.util.UUID;

public record CampaignInteractionId(UUID value) {
    public static CampaignInteractionId of(UUID id) {
        return new CampaignInteractionId(id);
    }

    public static CampaignInteractionId generate() {
        return new CampaignInteractionId(UUID.randomUUID());
    }
}
