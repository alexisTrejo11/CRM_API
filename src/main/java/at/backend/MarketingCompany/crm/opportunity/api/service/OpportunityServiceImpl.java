package at.backend.MarketingCompany.crm.opportunity.api.service;

import at.backend.MarketingCompany.crm.opportunity.infrastructure.DTOs.OpportunityInput;
import at.backend.MarketingCompany.crm.opportunity.infrastructure.autoMappers.OpportunityMappers;
import at.backend.MarketingCompany.customer.api.repository.CustomerModel;
import at.backend.MarketingCompany.crm.opportunity.domain.Opportunity;
import at.backend.MarketingCompany.customer.api.repository.CustomerRepository;
import at.backend.MarketingCompany.crm.opportunity.api.repository.OpportunityRepository;
import at.backend.MarketingCompany.common.exceptions.BusinessLogicException;
import at.backend.MarketingCompany.common.service.CommonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements CommonService<Opportunity, OpportunityInput, Long> {

    public final OpportunityRepository opportunityRepository;
    public final OpportunityMappers opportunityMappers;
    public final CustomerRepository customerRepository;

    @Override
    public Page<Opportunity> getAll(Pageable pageable) {
        return opportunityRepository.findAll(pageable);
    }

    @Override
    public Opportunity getById(Long id) {
        return getOpportunity(id);
    }

    @Override
    public Opportunity create(OpportunityInput input) {
        Opportunity newOpportunity = opportunityMappers.inputToEntity(input);

        if (input.customerId() != null) {
            newOpportunity.setCustomerModel(getCustomer(input.customerId()));
        }

        opportunityRepository.saveAndFlush(newOpportunity);

        return newOpportunity;
    }

    @Override
    public Opportunity update(Long id, OpportunityInput input) {
        Opportunity existingOpportunity = getOpportunity(id);

        Opportunity updatedOpportunity = opportunityMappers.inputToUpdatedEntity(existingOpportunity, input);

        if (input.customerId() != null) {
            updatedOpportunity.setCustomerModel(getCustomer(input.customerId()));
        }

        opportunityRepository.saveAndFlush(updatedOpportunity);

        return updatedOpportunity;
    }

    @Override
    public void delete(Long id) {
        Opportunity opportunity = getOpportunity(id);

        opportunityRepository.delete(opportunity);
    }

    @Override
    public void validate(OpportunityInput input) {
        if (input.customerId() != null) {
            Optional<CustomerModel> customer = customerRepository.findById(input.customerId());
            if (customer.isEmpty()) {
                throw new EntityNotFoundException("CustomerModel Not Found");
            }

        }

        if (input.amount() != null) {
            if (input.amount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BusinessLogicException("Amount must be greater than zero");
            }

            BigDecimal maxAmount = new BigDecimal("1000000"); // Max Limit
            if (input.amount().compareTo(maxAmount) > 0) {
                throw new BusinessLogicException("Amount must not exceed " + maxAmount);
            }
        }

        if (input.expectedCloseDate() != null) {
            if (input.expectedCloseDate().isBefore(LocalDate.now())) {
                throw new BusinessLogicException("Expected close date cannot be in the past");
            }

            LocalDate maxCloseDate = LocalDate.now().plusYears(1); // Max Date allowed (1 year)
            if (input.expectedCloseDate().isAfter(maxCloseDate)) {
                throw new BusinessLogicException("Expected close date cannot be more than one year in the future");
            }
        }

    }

    private CustomerModel getCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("CustomerModel Not found"));
    }

    private Opportunity getOpportunity(Long id) {
        return opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity Not found"));
    }
}
