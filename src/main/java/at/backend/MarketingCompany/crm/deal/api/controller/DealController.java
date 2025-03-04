package at.backend.MarketingCompany.crm.deal.api.controller;

import at.backend.MarketingCompany.crm.deal.api.service.DealServiceImpl;
import at.backend.MarketingCompany.crm.deal.infrastructure.DTOs.DealInput;
import at.backend.MarketingCompany.common.utils.PageInput;
import at.backend.MarketingCompany.crm.deal.domain.Deal;
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

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class DealController {
    private final DealServiceImpl service;

    @QueryMapping
    public Page<Deal> getAllDeals(@Argument PageInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.size());

        return service.getAll(pageable);
    }

    @QueryMapping
    public Deal getDealById(@Argument UUID id) {
        return service.getById(id);
    }

    @MutationMapping
    public Deal createDeal(@Valid @Argument DealInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public Deal updateDeal(@Valid @Argument UUID id, @Argument DealInput input) {
        service.validate(input);

        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteDeal(@Argument UUID id) {
        service.delete(id);
        return true;
    }
}
