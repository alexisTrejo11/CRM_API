package at.backend.MarketingCompany.marketing.activity.api.controller;

import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.api.service.CampaignActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CampaignActivityController {

    private final CampaignActivityService campaignActivityService;

    @QueryMapping
    public CampaignActivityDTO getActivityById(@Argument UUID id) {
        return campaignActivityService.getById(id);
    }

    @MutationMapping
    public CampaignActivityDTO createActivity(@Valid @Argument CampaignActivityInsertDTO input) {
        return campaignActivityService.create(input);
    }

    @MutationMapping
    public CampaignActivityDTO updateActivity(@Valid @Argument CampaignActivityInsertDTO input,
                                               @Argument UUID id) {
        return campaignActivityService.update(id, input);
    }

    @MutationMapping
    public boolean deleteActivity(@Argument UUID id) {
        campaignActivityService.delete(id);
        
        return true;
    }
}
