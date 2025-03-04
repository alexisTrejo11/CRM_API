package at.backend.MarketingCompany.marketing.interaction.api.service;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionDTO;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionInsertDTO;

import java.util.List;
import java.util.Map;

public interface CampaignInteractionService extends CommonService<CampaignInteractionDTO, CampaignInteractionInsertDTO, Long> {
    List<CampaignInteractionDTO> getInteractionsByCampaignId(Long campaignId);
    List<CampaignInteractionDTO> getInteractionsByCustomerId(Long customerId);
    CampaignInteractionDTO updateInteractionProperties(Long id, Map<String, String> properties);
}
