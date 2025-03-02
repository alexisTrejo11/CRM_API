package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CRM.Models.Customer;
import at.backend.CRM.CommonClasses.Service.CommonService;
import at.backend.CRM.MarketingProject.DTOs.CustomerSegmentDTO;
import at.backend.CRM.MarketingProject.DTOs.CustomerSegmentInsertDTO;
import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignDTO;
import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignInsertDTO;
import at.backend.CRM.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.MarketingProject.Utils.Enums.CampaignStatus;

import java.util.List;
import java.util.Map;

public interface MarketingCampaignService extends CommonService<MarketingCampaignDTO, MarketingCampaignInsertDTO> {
    MarketingCampaignDTO pauseCampaign(Long id);
    MarketingCampaignDTO activateCampaign(Long id);
    List<MarketingCampaignDTO> getCampaignsByStatus(CampaignStatus status);
    MarketingCampaignDTO updateCampaignTargets(Long id, Map<String, Double> targets);
}
