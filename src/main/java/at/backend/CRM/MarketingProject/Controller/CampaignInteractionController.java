package at.backend.CRM.MarketingProject.Controller;

import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.CRM.MarketingProject.Service.CampaignInteractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CampaignInteractionController {
    private final CampaignInteractionService campaignInteractionService;

    @QueryMapping
    public CampaignInteractionDTO getCampaignInteractionById(@Argument Long id) {
        return campaignInteractionService.getInteractionById(id);
    }

    @MutationMapping
    public CampaignInteractionDTO createCampaignInteraction(@Valid @Argument CampaignInteractionInsertDTO input) {
        return campaignInteractionService.createInteraction(input);
    }

    @MutationMapping
    public CampaignInteractionDTO updateCampaignInteraction(@Valid @Argument CampaignInteractionInsertDTO input,
                                               @PathVariable Long id) {
        return campaignInteractionService.updateInteraction(id, input);
    }

    @MutationMapping
    public boolean deleteCampaignInteraction(@Argument Long id) {
        campaignInteractionService.deleteInteraction(id);
        
        return true;
    }
}
