package at.backend.MarketingCompany.marketing.activity.api.service;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;

import java.util.List;
import java.util.UUID;

public interface CampaignActivityService extends CommonService<CampaignActivityDTO, CampaignActivityInsertDTO, UUID> {
    List<CampaignActivityModel> getActivitiesByCampaignId(UUID campaignId);
    List<CampaignActivityModel> getActivitiesByStatus(ActivityStatus status);

    CampaignActivityDTO startActivity(UUID id);
    CampaignActivityDTO completeActivity(UUID id);
}
