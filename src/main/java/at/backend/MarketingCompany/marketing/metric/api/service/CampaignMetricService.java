package at.backend.MarketingCompany.marketing.metric.api.service;


import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricDTO;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricInsertDTO;
import at.backend.MarketingCompany.marketing.metric.api.repository.CampaignMetric;

import java.util.List;

public interface CampaignMetricService extends CommonService<CampaignMetricDTO, CampaignMetricInsertDTO, Long> {
    List<CampaignMetric> getMetricsByCampaignId(Long campaignId);
}