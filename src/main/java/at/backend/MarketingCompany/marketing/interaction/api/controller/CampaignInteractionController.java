package at.backend.MarketingCompany.marketing.interaction.api.controller;

import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionDTO;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingCompany.marketing.interaction.api.service.CampaignInteractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CampaignInteractionController {

    private final CampaignInteractionService campaignInteractionService;

    @QueryMapping
    public CampaignInteractionDTO getCampaignInteractionById(@Argument Long id) {
        return campaignInteractionService.getById(id);
    }

    @MutationMapping
    public CampaignInteractionDTO createCampaignInteraction(@Valid @Argument CampaignInteractionInsertDTO input) {
        return campaignInteractionService.create(input);
    }

    @MutationMapping
    public CampaignInteractionDTO updateCampaignInteraction(@Valid @Argument CampaignInteractionInsertDTO input,
                                               @Argument Long id) {
        return campaignInteractionService.update(id, input);
    }

    @MutationMapping
    public boolean deleteCampaignInteraction(@Argument Long id) {
        campaignInteractionService.delete(id);
        
        return true;
    }
}
