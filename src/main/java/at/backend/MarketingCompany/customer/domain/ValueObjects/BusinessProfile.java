package at.backend.MarketingCompany.customer.domain.ValueObjects;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class BusinessProfile {
    private final String company;
    private final String industry;
    private final String brandVoice;
    private final String brandColors;
    private final String targetMarket;
    private final Set<String> competitorUrls;
    private final String socialMediaHandles;

    public boolean hasCompetitors() {
        return competitorUrls != null && !competitorUrls.isEmpty();
    }

    public boolean hasSocialMediaHandles() {
        return socialMediaHandles != null && !socialMediaHandles.isBlank();
    }
}