package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.MarketingCampaignDTO;
import at.backend.MarketingProject.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingProject.Service.MarketingCampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marketing-campaigns")
@RequiredArgsConstructor
public class MarketingCampaignController {
    private final MarketingCampaignService marketingCampaignService;

    @QueryMapping("/{id}")
    public MarketingCampaignDTO getCampaignById(@PathVariable Long id) {
        return marketingCampaignService.getCampaignById(id);
    }

    @MutationMapping
    public MarketingCampaignDTO CreateCampaign(@Valid @RequestBody MarketingCampaignInsertDTO insertDTO) {
        return marketingCampaignService.createCampaign(insertDTO);
    }

    @MutationMapping("/{id}")
    public MarketingCampaignDTO updateCampaign(@Valid @RequestBody MarketingCampaignInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return marketingCampaignService.updateCampaign(id, insertDTO);
    }


    @MutationMapping("/{id}")
    public boolean deleteCampaign(@PathVariable Long id) {
        marketingCampaignService.deleteCampaign(id);

        return true;
    }
}
