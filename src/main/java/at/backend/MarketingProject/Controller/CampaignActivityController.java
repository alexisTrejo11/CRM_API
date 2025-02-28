package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CampaignActivityDTO;
import at.backend.MarketingProject.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingProject.Service.CampaignActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing-campaigns/activities")
@RequiredArgsConstructor
public class CampaignActivityController {
    private final CampaignActivityService campaignActivityService;

    @GetMapping("/{id}")
    public CampaignActivityDTO getActivityByID(@PathVariable Long id) {
        return campaignActivityService.getActivityById(id);
    }

    @PostMapping
    public CampaignActivityDTO CreateActivity(@Valid @RequestBody CampaignActivityInsertDTO insertDTO) {
        return campaignActivityService.createActivity(insertDTO);
    }

    @PutMapping("/{id}")
    public CampaignActivityDTO updateActivity(@Valid @RequestBody CampaignActivityInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignActivityService.updateActivity(id, insertDTO);
    }


    @DeleteMapping("/{id}")
    public boolean deleteActivityByID(@PathVariable Long id) {
        campaignActivityService.deleteActivity(id);
        
        return true;
    }
}
