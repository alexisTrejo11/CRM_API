package at.backend.MarketingCompany.marketing.campaign.api.controller;

import at.backend.MarketingCompany.marketing.campaign.api.service.MarketingCampaignService;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MarketingCampaignController {

    private final MarketingCampaignService marketingCampaignService;

    @QueryMapping
    public MarketingCampaignDTO getCampaignById(@Argument String id) {
        return marketingCampaignService.getById(UUID.fromString(id));
    }

    @MutationMapping
    public MarketingCampaignDTO createCampaign(@Valid @Argument MarketingCampaignInsertDTO input) {
        return marketingCampaignService.create(input);
    }

    @MutationMapping
    public MarketingCampaignDTO updateCampaign(@Valid @Argument MarketingCampaignInsertDTO input,
                                               @Argument String id) {
        return marketingCampaignService.update(UUID.fromString(id), input);
    }

    @MutationMapping
    public boolean deleteCampaign(@Argument String id) {
        marketingCampaignService.delete(UUID.fromString(id));

        return true;
    }
}
