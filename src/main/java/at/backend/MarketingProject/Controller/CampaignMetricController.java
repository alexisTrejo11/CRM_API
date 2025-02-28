package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CampaignMetricDTO;
import at.backend.MarketingProject.DTOs.CampaignMetricInsertDTO;
import at.backend.MarketingProject.Service.CampaignMetricService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marketing-campaigns/metrics")
@RequiredArgsConstructor
public class CampaignMetricController {
    private final CampaignMetricService campaignMetricService;

    @QueryMapping("/{id}")
    public CampaignMetricDTO getMetricById(@PathVariable Long id) {
        return campaignMetricService.getMetricById(id);
    }

    @MutationMapping
    public CampaignMetricDTO CreateMetric(@Valid @RequestBody CampaignMetricInsertDTO insertDTO) {
        return campaignMetricService.createMetric(insertDTO);
    }

    @MutationMapping("/{id}")
    public CampaignMetricDTO updateMetric(@Valid @RequestBody CampaignMetricInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignMetricService.updateMetric(id, insertDTO);
    }


    @MutationMapping("/{id}")
    public boolean deleteMetric(@PathVariable Long id) {
        campaignMetricService.deleteMetric(id);
        
        return true;
    }
}
