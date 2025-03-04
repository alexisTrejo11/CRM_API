package at.backend.MarketingCompany.marketing.campaign.api.service;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MarketingCampaignService extends CommonService<MarketingCampaignDTO, MarketingCampaignInsertDTO, UUID> {
    /*
     List<MetricDTO> getCampaignMetrics(UUID campaignId);
     MarketingCampaignDTO getCampaignDetails(UUID campaignId);
     MarketingCampaignDTO addInteraction(UUID campaignId, InteractionInput interaction);
     */

    List<MarketingCampaignDTO> getActiveCampaigns(LocalDate date);

    MarketingCampaignDTO startCampaign(UUID campaignId);
    MarketingCampaignDTO completeCampaign(UUID campaignId);
    MarketingCampaignDTO updateBudget(UUID campaignId, BigDecimal newBudget);

    double calculateCampaignROI(UUID campaignId);
    void archiveCampaign(UUID campaignId);
}
