package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingProject.Service.CampaignInteractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing-campaigns/interactions")
@RequiredArgsConstructor
public class CampaignInteractionController {
    private final CampaignInteractionService campaignInteractionService;

    @GetMapping("/{id}")
    public CampaignInteractionDTO getInteractionByID(@PathVariable Long id) {
        return campaignInteractionService.getInteractionById(id);
    }

    @PostMapping
    public CampaignInteractionDTO CreateInteraction(@Valid @RequestBody CampaignInteractionInsertDTO insertDTO) {
        return campaignInteractionService.createInteraction(insertDTO);
    }

    @PutMapping("/{id}")
    public CampaignInteractionDTO updateInteraction(@Valid @RequestBody CampaignInteractionInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignInteractionService.updateInteraction(id, insertDTO);
    }


    @DeleteMapping("/{id}")
    public boolean deleteInteractionByID(@PathVariable Long id) {
        campaignInteractionService.deleteInteraction(id);
        
        return true;
    }
}
