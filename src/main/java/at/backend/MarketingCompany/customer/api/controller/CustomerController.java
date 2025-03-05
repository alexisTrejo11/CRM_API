package at.backend.MarketingCompany.customer.api.controller;

import at.backend.MarketingCompany.customer.api.service.CustomerService;
import at.backend.MarketingCompany.customer.infrastructure.CustomerDTO;
import at.backend.MarketingCompany.customer.infrastructure.CustomerInsertDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @QueryMapping
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        return service.getAll(pageable);
    }

    @QueryMapping
    public CustomerDTO getCustomerById(@Argument UUID id) {
        return service.getById(id);
    }

    @QueryMapping
    public boolean isCustomerBlocked(@Argument UUID id) {
        return service.isBlocked(id);
    }

    @QueryMapping
    public boolean hasSocialMediaHandles(@Argument UUID id) {
        return service.hasSocialMediaHandles(id);
    }

    @QueryMapping
    public boolean hasCompetitors(@Argument UUID id) {
        return service.hasCompetitors(id);
    }

    @MutationMapping
    public CustomerDTO createCustomer(@Argument @Valid CustomerInsertDTO input) {
        return service.create(input);
    }

    @MutationMapping
    public CustomerDTO updateCustomer(
            @Argument UUID id,
            @Argument CustomerInsertDTO input
    ) {
        return service.update(id, input);
    }

    @MutationMapping
    public void deleteCustomer(@Argument UUID id) {
        service.delete(id);
    }
}