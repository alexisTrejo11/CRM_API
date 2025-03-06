package at.backend.MarketingCompany.marketing.activity.api.controller;

import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.api.service.CampaignActivityService;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CampaignActivityController {

    private final CampaignActivityService campaignActivityService;

    @QueryMapping
    public CampaignActivityDTO getActivityById(@Argument UUID id) {
        return campaignActivityService.getById(id);
    }

    @QueryMapping
    public List<CampaignActivityDTO> getActivitiesByCampaignId(@Argument UUID campaignId) {
        return campaignActivityService.getActivitiesByCampaignId(campaignId);
    }

    @QueryMapping
    public List<CampaignActivityDTO> getActivitiesByStatus(@Argument ActivityStatus status) {
        return campaignActivityService.getActivitiesByStatus(status);
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

    @MutationMapping
    public CampaignActivityDTO startActivity(@Argument UUID id) {
        return campaignActivityService.startActivity(id);
    }

    @MutationMapping
    public CampaignActivityDTO completeActivity(@Argument UUID id) {
        return campaignActivityService.completeActivity(id);
    }
}
