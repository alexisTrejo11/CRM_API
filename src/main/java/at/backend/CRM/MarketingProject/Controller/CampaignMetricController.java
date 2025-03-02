package at.backend.CRM.MarketingProject.Controller;

import at.backend.CRM.MarketingProject.DTOs.CampaignMetricDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricInsertDTO;
import at.backend.CRM.MarketingProject.Service.CampaignMetricService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CampaignMetricController {

    private final CampaignMetricService campaignMetricService;

    @QueryMapping
    public CampaignMetricDTO getMetricById(@Argument Long id) {
        return campaignMetricService.getMetricById(id);
    }

    @MutationMapping
    public CampaignMetricDTO createMetric(@Valid @Argument CampaignMetricInsertDTO input) {
        return campaignMetricService.createMetric(input);
    }

    @MutationMapping
    public CampaignMetricDTO updateMetric(@Valid @Argument CampaignMetricInsertDTO input,
                                               @Argument Long id) {
        return campaignMetricService.updateMetric(id, input);
    }


    @MutationMapping
    public boolean deleteMetric(@Argument Long id) {
        campaignMetricService.deleteMetric(id);
        
        return true;
    }
}
