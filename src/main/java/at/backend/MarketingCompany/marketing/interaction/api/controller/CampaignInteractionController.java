package at.backend.MarketingCompany.marketing.interaction.api.controller;

import at.backend.MarketingCompany.common.utils.InputValidator;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionDTO;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingCompany.marketing.interaction.api.service.CampaignInteractionService;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.ConversionSummaryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CampaignInteractionController {

    private final CampaignInteractionService campaignInteractionService;
    private final InputValidator validator;

    @QueryMapping
        public CampaignInteractionDTO getCampaignInteractionById(@Argument UUID id) {
        return campaignInteractionService.getById(id);
    }

    @MutationMapping
    public CampaignInteractionDTO createCampaignInteraction(@Valid @Argument CampaignInteractionInsertDTO input) {
        validator.validate(input);
        return campaignInteractionService.create(input);
    }

    @MutationMapping
    public CampaignInteractionDTO updateCampaignInteraction(@Valid @Argument CampaignInteractionInsertDTO input,
                                               @Argument UUID id) {
        validator.validate(input);
        return campaignInteractionService.update(id, input);
    }

    @MutationMapping
    public boolean deleteCampaignInteraction(@Argument UUID id) {
        campaignInteractionService.delete(id);
        
        return true;
    }

    @QueryMapping
    public List<CampaignInteractionDTO> getCampaignInteractionsByCampaign(
            @Argument UUID campaignId,
            @Argument LocalDateTime from,
            @Argument LocalDateTime to
    ) {
        return campaignInteractionService.getByCampaignId(campaignId, from, to);
    }

    @QueryMapping
    public List<CampaignInteractionDTO> getCampaignInteractionsByCustomer(
            @Argument UUID customerId
    ) {
        return campaignInteractionService.getByCustomerId(customerId);
    }

    @QueryMapping
    public ConversionSummaryDTO getConversionSummary(
            @Argument UUID campaignId
    ) {
        return campaignInteractionService.getConversionSummary(campaignId);
    }
}
