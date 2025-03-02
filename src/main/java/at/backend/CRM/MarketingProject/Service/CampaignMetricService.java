package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CommonClasses.Service.CommonService;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignMetric;

import java.util.List;
import java.util.Map;

public interface CampaignMetricService extends CommonService<CampaignMetricDTO, CampaignMetricInsertDTO> {
    List<CampaignMetric> getMetricsByCampaignId(Long campaignId);
}
