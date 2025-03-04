package at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers;

import lombok.Value;

@Value(staticConstructor = "of")
public class CampaignId {
    String value;
}
