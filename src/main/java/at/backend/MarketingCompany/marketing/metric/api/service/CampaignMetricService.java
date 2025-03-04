package at.backend.MarketingCompany.marketing.metric.api.service;


import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricDTO;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricInsertDTO;
import at.backend.MarketingCompany.marketing.metric.api.repository.CampaignMetricModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CampaignMetricService extends CommonService<CampaignMetricDTO, CampaignMetricInsertDTO, UUID> {
    List<CampaignMetricDTO> getMetricsByCampaignId(UUID campaignId);
    boolean isTargetAchieved(UUID id);
    BigDecimal calculateCurrentPerformance(UUID id);
}