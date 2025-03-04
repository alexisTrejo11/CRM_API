package at.backend.MarketingCompany.marketing.attribution.api.service;

import at.backend.MarketingCompany.marketing.attribution.domain.CampaignAttribution;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionDTO;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;

import java.util.List;

public interface AttributionCalculator {
    CampaignAttribution initialCalculation(CampaignAttribution attribution);
    CampaignAttribution recalculateForModel(CampaignAttribution attribution);
    CampaignAttribution adjustForNewTouch(CampaignAttribution attribution);
    CampaignAttributionDTO calculateRevenueDistribution(List<CampaignAttribution> attributions);
}
