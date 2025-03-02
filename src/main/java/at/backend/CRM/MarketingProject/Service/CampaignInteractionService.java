package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CommonClasses.Service.CommonService;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionInsertDTO;

import java.util.List;
import java.util.Map;

public interface CampaignInteractionService extends CommonService<CampaignInteractionDTO, CampaignInteractionInsertDTO> {
    List<CampaignInteractionDTO> getInteractionsByCampaignId(Long campaignId);
    List<CampaignInteractionDTO> getInteractionsByCustomerId(Long customerId);
    CampaignInteractionDTO updateInteractionProperties(Long id, Map<String, String> properties);
}
