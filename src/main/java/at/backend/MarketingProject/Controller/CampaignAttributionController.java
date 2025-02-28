package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CampaignAttributionDTO;
import at.backend.MarketingProject.DTOs.CampaignAttributionInsertDTO;
import at.backend.MarketingProject.Service.CampaignAttributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marketing-campaigns/attributions")
@RequiredArgsConstructor
public class CampaignAttributionController {
    private final CampaignAttributionService campaignAttributionService;

    @QueryMapping("/{id}")
    public CampaignAttributionDTO getAttributionById(@PathVariable Long id) {
        return campaignAttributionService.getAttributionById(id);
    }

    @MutationMapping
    public CampaignAttributionDTO createAttribution(@Valid @RequestBody CampaignAttributionInsertDTO insertDTO) {
        return campaignAttributionService.createAttribution(insertDTO);
    }

    @MutationMapping("/{id}")
    public CampaignAttributionDTO updateAttribution(@Valid @RequestBody CampaignAttributionInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignAttributionService.updateAttribution(id, insertDTO);
    }


    @MutationMapping("/{id}")
    public boolean deleteAttribution(@PathVariable Long id) {
        campaignAttributionService.deleteAttribution(id);
        
        return true;
    }
}
