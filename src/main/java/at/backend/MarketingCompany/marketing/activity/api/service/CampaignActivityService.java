package at.backend.MarketingCompany.marketing.activity.api.service;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;

import java.util.List;

public interface CampaignActivityService extends CommonService<CampaignActivityDTO, CampaignActivityInsertDTO, Long> {
    List<CampaignActivityModel> getActivitiesByCampaignId(Long campaignId);
    List<CampaignActivityModel> getActivitiesByStatus(ActivityStatus status);

    CampaignActivityDTO startActivity(Long id);
    CampaignActivityDTO completeActivity(Long id);
}
