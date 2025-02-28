package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.MarketingCampaignDTO;
import at.backend.MarketingProject.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingProject.Service.MarketingCampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing-campaigns")
@RequiredArgsConstructor
public class MarketingCampaignController {
    private final MarketingCampaignService marketingCampaignService;

    @GetMapping("/{id}")
    public MarketingCampaignDTO getCampaignByID(@PathVariable Long id) {
        return marketingCampaignService.getCampaignById(id);
    }

    @PostMapping
    public MarketingCampaignDTO CreateCampaign(@Valid @RequestBody MarketingCampaignInsertDTO insertDTO) {
        return marketingCampaignService.createCampaign(insertDTO);
    }

    @PutMapping("/{id}")
    public MarketingCampaignDTO updateCampaign(@Valid @RequestBody MarketingCampaignInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return marketingCampaignService.updateCampaign(id, insertDTO);
    }


    @DeleteMapping("/{id}")
    public boolean deleteCampaignByID(@PathVariable Long id) {
        marketingCampaignService.deleteCampaign(id);

        return true;
    }
}
