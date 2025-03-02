package at.backend.CRM.MarketingProject.Controller;

import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignDTO;
import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignInsertDTO;
import at.backend.CRM.MarketingProject.Service.MarketingCampaignServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MarketingCampaignController {

    private final MarketingCampaignServiceImpl marketingCampaignServiceImpl;

    @QueryMapping
    public MarketingCampaignDTO getCampaignById(@Argument Long id) {
        return marketingCampaignServiceImpl.getCampaignById(id);
    }

    @MutationMapping
    public MarketingCampaignDTO createCampaign(@Valid @Argument MarketingCampaignInsertDTO input) {
        return marketingCampaignServiceImpl.createCampaign(input);
    }

    @MutationMapping
    public MarketingCampaignDTO updateCampaign(@Valid @Argument MarketingCampaignInsertDTO input,
                                               @Argument Long id) {
        return marketingCampaignServiceImpl.updateCampaign(id, input);
    }


    @MutationMapping
    public boolean deleteCampaign(@Argument Long id) {
        marketingCampaignServiceImpl.deleteCampaign(id);

        return true;
    }
}
