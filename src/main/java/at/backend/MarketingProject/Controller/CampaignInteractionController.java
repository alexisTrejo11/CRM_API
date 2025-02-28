package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingProject.Service.CampaignInteractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marketing-campaigns/interactions")
@RequiredArgsConstructor
public class CampaignInteractionController {
    private final CampaignInteractionService campaignInteractionService;

    @QueryMapping("/{id}")
    public CampaignInteractionDTO getInteractionById(@PathVariable Long id) {
        return campaignInteractionService.getInteractionById(id);
    }

    @MutationMapping
    public CampaignInteractionDTO CreateInteraction(@Valid @RequestBody CampaignInteractionInsertDTO insertDTO) {
        return campaignInteractionService.createInteraction(insertDTO);
    }

    @MutationMapping("/{id}")
    public CampaignInteractionDTO updateInteraction(@Valid @RequestBody CampaignInteractionInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignInteractionService.updateInteraction(id, insertDTO);
    }


    @MutationMapping("/{id}")
    public boolean deleteInteraction(@PathVariable Long id) {
        campaignInteractionService.deleteInteraction(id);
        
        return true;
    }
}
