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

    private final CampaignInteractionService campaignInteractionServiceImpl;

    @QueryMapping
    public CampaignInteractionDTO getCampaignInteractionById(@Argument Long id) {
        return campaignInteractionServiceImpl.getById(id);
    }

    @MutationMapping
    public CampaignInteractionDTO createCampaignInteraction(@Valid @Argument CampaignInteractionInsertDTO input) {
        return campaignInteractionServiceImpl.create(input);
    }

    @MutationMapping
    public CampaignInteractionDTO updateCampaignInteraction(@Valid @Argument CampaignInteractionInsertDTO input,
                                               @PathVariable Long id) {
        return campaignInteractionServiceImpl.update(id, input);
    }

    @MutationMapping
    public boolean deleteCampaignInteraction(@Argument Long id) {
        campaignInteractionServiceImpl.delete(id);
        
        return true;
    }
}
