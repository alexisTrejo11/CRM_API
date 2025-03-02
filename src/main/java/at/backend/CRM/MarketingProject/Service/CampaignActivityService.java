package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CommonClasses.Service.CommonService;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignActivity;
import at.backend.CRM.MarketingProject.Utils.Enums.ActivityStatus;

import java.util.List;

public interface CampaignActivityService extends CommonService<CampaignActivityDTO, CampaignActivityInsertDTO> {
    List<CampaignActivity> getActivitiesByCampaignId(Long campaignId);
    List<CampaignActivity> getActivitiesByStatus(ActivityStatus status);

    CampaignActivityDTO startActivity(Long id);
    CampaignActivityDTO completeActivity(Long id);
}
