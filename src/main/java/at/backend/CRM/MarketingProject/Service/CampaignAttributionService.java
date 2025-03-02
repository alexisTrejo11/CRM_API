package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CommonClasses.Service.CommonService;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityInsertDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignActivity;
import at.backend.CRM.MarketingProject.Models.CampaignAttribution;
import at.backend.CRM.MarketingProject.Utils.Enums.ActivityStatus;

import java.util.List;

public interface CampaignAttributionService extends CommonService<CampaignAttributionDTO, CampaignAttributionInsertDTO> {
    List<CampaignAttributionDTO> getAttributionsByDealId(Long dealId);
    List<CampaignAttributionDTO> getAttributionsByCampaignId(Long campaignId);
}
