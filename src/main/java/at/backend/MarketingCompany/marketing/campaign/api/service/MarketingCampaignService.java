package at.backend.MarketingCompany.marketing.campaign.api.service;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignStatus;

import java.util.List;
import java.util.Map;

public interface MarketingCampaignService extends CommonService<MarketingCampaignDTO, MarketingCampaignInsertDTO> {
    MarketingCampaignDTO pauseCampaign(Long id);
    MarketingCampaignDTO activateCampaign(Long id);
    List<MarketingCampaignDTO> getCampaignsByStatus(CampaignStatus status);
    MarketingCampaignDTO updateCampaignTargets(Long id, Map<String, Double> targets);
}
