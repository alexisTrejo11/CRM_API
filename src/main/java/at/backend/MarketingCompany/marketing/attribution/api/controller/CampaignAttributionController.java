package at.backend.MarketingCompany.marketing.attribution.api.controller;

import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionDTO;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionInsertDTO;
import at.backend.MarketingCompany.marketing.attribution.api.service.CampaignAttributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CampaignAttributionController {

    private final CampaignAttributionService campaignAttributionServiceImpl;

    @QueryMapping
    public CampaignAttributionDTO getAttributionById(@Argument Long id) {
        return campaignAttributionServiceImpl.getById(id);
    }

    @MutationMapping
    public CampaignAttributionDTO createAttribution(@Valid @Argument CampaignAttributionInsertDTO input) {
        return campaignAttributionServiceImpl.create(input);
    }

    @MutationMapping
    public CampaignAttributionDTO updateAttribution(@Valid @Argument CampaignAttributionInsertDTO input,
                                               @Argument Long id) {
        return campaignAttributionServiceImpl.update(id, input);
    }


    @MutationMapping
    public boolean deleteAttribution(@Argument Long id) {
        campaignAttributionServiceImpl.delete(id);
        
        return true;
    }
}
