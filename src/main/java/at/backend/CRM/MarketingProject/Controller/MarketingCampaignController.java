package at.backend.CRM.MarketingProject.Controller;

import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignDTO;
import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignInsertDTO;
import at.backend.CRM.MarketingProject.Service.MarketingCampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MarketingCampaignController {
    private final MarketingCampaignService marketingCampaignService;

    @QueryMapping
    public MarketingCampaignDTO getCampaignById(@Argument Long id) {
        return marketingCampaignService.getCampaignById(id);
    }

    @MutationMapping
    public MarketingCampaignDTO CreateCampaign(@Valid @Argument MarketingCampaignInsertDTO insertDTO) {
        return marketingCampaignService.createCampaign(insertDTO);
    }

    @MutationMapping
    public MarketingCampaignDTO updateCampaign(@Valid @Argument MarketingCampaignInsertDTO insertDTO,
                                               @Argument Long id) {
        return marketingCampaignService.updateCampaign(id, insertDTO);
    }


    @MutationMapping
    public boolean deleteCampaign(@Argument Long id) {
        marketingCampaignService.deleteCampaign(id);

        return true;
    }
}
