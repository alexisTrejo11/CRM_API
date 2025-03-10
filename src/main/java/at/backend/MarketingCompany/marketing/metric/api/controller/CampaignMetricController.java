package at.backend.MarketingCompany.marketing.metric.api.controller;

import at.backend.MarketingCompany.common.utils.InputValidator;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricDTO;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricInsertDTO;
import at.backend.MarketingCompany.marketing.metric.api.service.CampaignMetricService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CampaignMetricController {

    private final CampaignMetricService campaignMetricService;
    private final InputValidator validator;

    @QueryMapping
    public CampaignMetricDTO getMetricById(@Argument UUID id) {
        return campaignMetricService.getById(id);
    }

    @MutationMapping
    public CampaignMetricDTO createMetric(@Valid @Argument CampaignMetricInsertDTO input) {
        validator.validate(input);

        return campaignMetricService.create(input);
    }

    @MutationMapping
    public CampaignMetricDTO updateMetric(@Valid @Argument CampaignMetricInsertDTO input,
                                          @Argument UUID id) {
        validator.validate(input);

        return campaignMetricService.update(id, input);
    }

    @MutationMapping
    public boolean deleteMetric(@Argument UUID id) {
        campaignMetricService.delete(id);

        return true;
    }
}
