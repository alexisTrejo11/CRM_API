package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CampaignMetricDTO;
import at.backend.MarketingProject.DTOs.CampaignMetricInsertDTO;
import at.backend.MarketingProject.Service.CampaignMetricService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing-campaigns/metrics")
@RequiredArgsConstructor
public class CampaignMetricController {
    private final CampaignMetricService campaignMetricService;

    @GetMapping("/{id}")
    public CampaignMetricDTO getMetricByID(@PathVariable Long id) {
        return campaignMetricService.getMetricById(id);
    }

    @PostMapping
    public CampaignMetricDTO CreateMetric(@Valid @RequestBody CampaignMetricInsertDTO insertDTO) {
        return campaignMetricService.createMetric(insertDTO);
    }

    @PutMapping("/{id}")
    public CampaignMetricDTO updateMetric(@Valid @RequestBody CampaignMetricInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignMetricService.updateMetric(id, insertDTO);
    }


    @DeleteMapping("/{id}")
    public boolean deleteMetricByID(@PathVariable Long id) {
        campaignMetricService.deleteMetric(id);
        
        return true;
    }
}
