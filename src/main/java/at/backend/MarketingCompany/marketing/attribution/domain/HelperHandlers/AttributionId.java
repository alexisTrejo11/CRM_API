package at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers;

import lombok.Value;

@Value(staticConstructor = "of")
public class AttributionId {
    Long value;
}
