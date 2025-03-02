package at.backend.CRM.CRM.Service;

import at.backend.CRM.CRM.Inputs.OpportunityInput;
import at.backend.CRM.CRM.Mappers.OpportunityMappers;
import at.backend.CRM.CRM.Models.Customer;
import at.backend.CRM.CRM.Models.Opportunity;
import at.backend.CRM.CRM.Repository.CustomerRepository;
import at.backend.CRM.CRM.Repository.OpportunityRepository;
import at.backend.CRM.CommonClasses.Exceptions.BusinessLogicException;
import at.backend.CRM.CommonClasses.Service.CommonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements CommonService<Opportunity, OpportunityInput> {

    public final OpportunityRepository opportunityRepository;
    public final OpportunityMappers opportunityMappers;
    public final CustomerRepository customerRepository;

    @Override
    public Page<Opportunity> findAll(Pageable pageable) {
        return opportunityRepository.findAll(pageable);
    }

    @Override
    public Optional<Opportunity> findById(Long id) {
        return opportunityRepository.findById(id);
    }

    @Override
    public Opportunity create(OpportunityInput input) {
        Opportunity newOpportunity = opportunityMappers.inputToEntity(input);

        if (input.customerId() != null) {
            newOpportunity.setCustomer(getCustomer(input.customerId()));
        }

        opportunityRepository.saveAndFlush(newOpportunity);

        return newOpportunity;
    }

    @Override
    public Opportunity update(Long id, OpportunityInput input) {
        Opportunity existingOpportunity =  opportunityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("opportunity not found"));

        Opportunity updatedOpportunity = opportunityMappers.inputToUpdatedEntity(existingOpportunity, input);

        if (input.customerId() != null) {
            updatedOpportunity.setCustomer(getCustomer(input.customerId()));
        }

        opportunityRepository.saveAndFlush(updatedOpportunity);

        return updatedOpportunity;
    }

    @Override
    public void delete(Long id) {
        boolean isOpportunityExisting = opportunityRepository.existsById(id);
        if (!isOpportunityExisting) {
            throw new EntityNotFoundException("opportunity not found");
        }

        opportunityRepository.deleteById(id);
    }

    @Override
    public void validate(OpportunityInput input) {
        if (input.customerId() != null) {
            Optional<Customer> customer = customerRepository.findById(input.customerId());
            if (customer.isEmpty()) {
                throw new EntityNotFoundException("Customer Not Found");
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

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer Not found"));
    }
}
