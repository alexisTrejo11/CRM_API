package at.backend.MarketingCompany.marketing.interaction.api.service;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionDTO;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.ConversionSummaryDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CampaignInteractionService extends CommonService<CampaignInteractionDTO, CampaignInteractionInsertDTO, UUID> {
    //CampaignInteractionDTO updateInteractionProperties(Long id, Map<String, String> properties);
    List<CampaignInteractionDTO> getByCampaignId(UUID campaignId, LocalDateTime from, LocalDateTime to);
    List<CampaignInteractionDTO> getByCustomerId(UUID customerId);
    ConversionSummaryDTO getConversionSummary(UUID campaignId);

}
