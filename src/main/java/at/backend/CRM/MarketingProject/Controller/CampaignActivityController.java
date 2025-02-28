package at.backend.CRM.MarketingProject.Controller;

import at.backend.CRM.MarketingProject.DTOs.CampaignActivityDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityInsertDTO;
import at.backend.CRM.MarketingProject.Service.CampaignActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CampaignActivityController {
    private final CampaignActivityService campaignActivityService;

    @QueryMapping
    public CampaignActivityDTO getActivityById(@Argument Long id) {
        return campaignActivityService.getActivityById(id);
    }

    @MutationMapping
    public CampaignActivityDTO createActivity(@Valid @Argument CampaignActivityInsertDTO insertDTO) {
        return campaignActivityService.createActivity(insertDTO);
    }

    @MutationMapping
    public CampaignActivityDTO updateActivity(@Valid @Argument CampaignActivityInsertDTO insertDTO,
                                               @Argument Long id) {
        return campaignActivityService.updateActivity(id, insertDTO);
    }


    @MutationMapping
    public boolean deleteActivity(@Argument Long id) {
        campaignActivityService.deleteActivity(id);
        
        return true;
    }
}
