package at.backend.CRM.MarketingProject.Controller;

import at.backend.CRM.MarketingProject.DTOs.CampaignMetricDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricInsertDTO;
import at.backend.CRM.MarketingProject.Service.CampaignMetricServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CampaignMetricController {

    private final CampaignMetricServiceImpl campaignMetricServiceImpl;

    @QueryMapping
    public CampaignMetricDTO getMetricById(@Argument Long id) {
        return campaignMetricServiceImpl.getById(id);
    }

    @MutationMapping
    public CampaignMetricDTO createMetric(@Valid @Argument CampaignMetricInsertDTO input) {
        return campaignMetricServiceImpl.create(input);
    }

    @MutationMapping
    public CampaignMetricDTO updateMetric(@Valid @Argument CampaignMetricInsertDTO input,
                                               @Argument Long id) {
        return campaignMetricServiceImpl.update(id, input);
    }


    @MutationMapping
    public boolean deleteMetric(@Argument Long id) {
        campaignMetricServiceImpl.delete(id);
        
        return true;
    }
}
