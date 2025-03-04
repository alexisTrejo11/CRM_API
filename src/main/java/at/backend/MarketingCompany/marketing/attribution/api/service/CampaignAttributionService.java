package at.backend.MarketingCompany.marketing.attribution.api.service;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionDTO;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionInsertDTO;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.TouchPointDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CampaignAttributionService extends CommonService<CampaignAttributionDTO, CampaignAttributionInsertDTO, UUID> {
    List<CampaignAttributionDTO> getAttributionsByDealId(UUID dealId);
    List<CampaignAttributionDTO> getAttributionsByCampaignId(UUID campaignId);
    void recalculateAllForCampaign(UUID campaignId);
    CampaignAttributionDTO getRevenueDistribution(UUID campaignId);
    CampaignAttributionDTO addTouchPoint(UUID attributionId, TouchPointDTO touch);
    Page<CampaignAttributionDTO> getAttributionsByCampaign(UUID campaignId, Pageable pageable);
}
