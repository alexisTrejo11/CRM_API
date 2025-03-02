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
        return campaignActivityService.getById(id);
    }

    @MutationMapping
    public CampaignActivityDTO createActivity(@Valid @Argument CampaignActivityInsertDTO input) {
        return campaignActivityService.create(input);
    }

    @MutationMapping
    public CampaignActivityDTO updateActivity(@Valid @Argument CampaignActivityInsertDTO input,
                                               @Argument Long id) {
        return campaignActivityService.update(id, input);
    }

    @MutationMapping
    public boolean deleteActivity(@Argument Long id) {
        campaignActivityService.delete(id);
        
        return true;
    }
}
