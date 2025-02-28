package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CampaignActivityDTO;
import at.backend.MarketingProject.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingProject.Service.CampaignActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marketing-campaigns/activities")
@RequiredArgsConstructor
public class CampaignActivityController {
    private final CampaignActivityService campaignActivityService;

    @QueryMapping("/{id}")
    public CampaignActivityDTO getActivityById(@PathVariable Long id) {
        return campaignActivityService.getActivityById(id);
    }

    @MutationMapping
    public CampaignActivityDTO CreateActivity(@Valid @RequestBody CampaignActivityInsertDTO insertDTO) {
        return campaignActivityService.createActivity(insertDTO);
    }

    @MutationMapping("/{id}")
    public CampaignActivityDTO updateActivity(@Valid @RequestBody CampaignActivityInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignActivityService.updateActivity(id, insertDTO);
    }


    @MutationMapping("/{id}")
    public boolean deleteActivity(@PathVariable Long id) {
        campaignActivityService.deleteActivity(id);
        
        return true;
    }
}
