package at.backend.MarketingCompany.crm.opportunity.api.controller;

import at.backend.MarketingCompany.crm.opportunity.infrastructure.DTOs.OpportunityInput;
import at.backend.MarketingCompany.common.utils.PageInput;
import at.backend.MarketingCompany.crm.opportunity.domain.Opportunity;
import at.backend.MarketingCompany.common.service.CommonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OpportunityController {
    
    private final CommonService<Opportunity, OpportunityInput, Long> service;

    @QueryMapping
    public Page<Opportunity> getAllOpportunities(@Argument PageInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.size());

        return service.getAll(pageable);
    }

    @QueryMapping
    public Opportunity getOpportunityById(@Argument Long id) {
        return service.getById(id);
    }

    @MutationMapping
    public Opportunity createOpportunity(@Valid @Argument OpportunityInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public Opportunity updateOpportunity(@Valid @Argument Long id, @Argument OpportunityInput input) {
        service.validate(input);

        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteOpportunity(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
