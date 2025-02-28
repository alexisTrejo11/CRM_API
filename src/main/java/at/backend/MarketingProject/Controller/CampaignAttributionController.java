package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CampaignAttributionDTO;
import at.backend.MarketingProject.DTOs.CampaignAttributionInsertDTO;
import at.backend.MarketingProject.Service.CampaignAttributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing-campaigns/attributions")
@RequiredArgsConstructor
public class CampaignAttributionController {
    private final CampaignAttributionService campaignAttributionService;

    @GetMapping("/{id}")
    public CampaignAttributionDTO getAttributionByID(@PathVariable Long id) {
        return campaignAttributionService.getAttributionById(id);
    }

    @PostMapping
    public CampaignAttributionDTO CreateAttribution(@Valid @RequestBody CampaignAttributionInsertDTO insertDTO) {
        return campaignAttributionService.createAttribution(insertDTO);
    }

    @PutMapping("/{id}")
    public CampaignAttributionDTO updateAttribution(@Valid @RequestBody CampaignAttributionInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignAttributionService.updateAttribution(id, insertDTO);
    }


    @DeleteMapping("/{id}")
    public boolean deleteAttributionByID(@PathVariable Long id) {
        campaignAttributionService.deleteAttribution(id);
        
        return true;
    }
}
