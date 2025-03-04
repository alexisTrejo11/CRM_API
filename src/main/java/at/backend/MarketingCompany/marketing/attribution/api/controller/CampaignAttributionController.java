package at.backend.MarketingCompany.marketing.attribution.api.controller;

import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionDTO;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionInsertDTO;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.TouchPointDTO;
import at.backend.MarketingCompany.marketing.attribution.api.service.CampaignAttributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CampaignAttributionController {

    private final CampaignAttributionService campaignAttributionService;

    @QueryMapping
    public Page<CampaignAttributionDTO> getAllAttributions(@Argument Pageable pageable) {
        return campaignAttributionService.getAll(pageable);
    }

    @QueryMapping
    public CampaignAttributionDTO getAttributionById(@Argument UUID id) {
        return campaignAttributionService.getById(id);
    }

    @QueryMapping
    public Page<CampaignAttributionDTO> getAttributionsByCampaign(@Argument UUID campaignId, @Argument Pageable pageable) {
        return campaignAttributionService.getAttributionsByCampaign(campaignId, pageable);
    }

    @QueryMapping
    public List<CampaignAttributionDTO> getAttributionsByDealId(@Argument UUID dealId) {
        return campaignAttributionService.getAttributionsByDealId(dealId);
    }

    @QueryMapping
    public List<CampaignAttributionDTO> getAttributionsByCampaignId(@Argument UUID campaignId) {
        return campaignAttributionService.getAttributionsByCampaignId(campaignId);
    }

    @MutationMapping
    public CampaignAttributionDTO createAttribution(@Valid @Argument CampaignAttributionInsertDTO input) {
        return campaignAttributionService.create(input);
    }

    @MutationMapping
    public CampaignAttributionDTO updateAttribution(@Valid @Argument CampaignAttributionInsertDTO input,
                                                    @Argument UUID id) {
        return campaignAttributionService.update(id, input);
    }

    @MutationMapping
    public boolean deleteAttribution(@Argument UUID id) {
        campaignAttributionService.delete(id);
        return true;
    }

    @MutationMapping
    public CampaignAttributionDTO addTouchPoint(@Argument UUID attributionId, @Argument TouchPointDTO touch) {
        return campaignAttributionService.addTouchPoint(attributionId, touch);
    }

    @MutationMapping
    public void recalculateAllForCampaign(@Argument UUID campaignId) {
        campaignAttributionService.recalculateAllForCampaign(campaignId);
    }

    @QueryMapping
    public CampaignAttributionDTO getRevenueDistribution(@Argument UUID campaignId) {
        return campaignAttributionService.getRevenueDistribution(campaignId);
    }
}
