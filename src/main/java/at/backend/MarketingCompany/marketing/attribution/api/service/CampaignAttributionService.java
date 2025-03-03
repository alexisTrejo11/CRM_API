package at.backend.MarketingCompany.marketing.attribution.api.service;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionDTO;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionInsertDTO;

import java.util.List;

public interface CampaignAttributionService extends CommonService<CampaignAttributionDTO, CampaignAttributionInsertDTO> {
    List<CampaignAttributionDTO> getAttributionsByDealId(Long dealId);
    List<CampaignAttributionDTO> getAttributionsByCampaignId(Long campaignId);
}
