package at.backend.CRM.Service;

import at.backend.CRM.Inputs.DealInput;
import at.backend.CRM.Mappers.DealMappers;
import at.backend.CRM.Models.Deal;
import at.backend.CRM.Models.Opportunity;
import at.backend.CRM.Models.ServicePackage;
import at.backend.CRM.Models.User;
import at.backend.CRM.Repository.DealRepository;
import at.backend.CRM.Repository.OpportunityRepository;
import at.backend.CRM.Repository.ServicePackageRepository;
import at.backend.CRM.Repository.UserRepository;
import at.backend.CRM.Utils.BusinessLogicException;
import at.backend.CRM.Utils.enums.DealStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements  CommonService<Deal, DealInput>{

    public final DealRepository customerRepository;
    public final DealMappers customerMappers;
    public final ServicePackageRepository servicePackageRepository;
    public final UserRepository userRepository;
    public final OpportunityRepository opportunityRepository;

    @Override
    public Page<Deal> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Optional<Deal> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Deal create(DealInput input) {
        Deal newDeal = customerMappers.inputToEntity(input);
        
        fetchRelationships(newDeal, input);
        customerRepository.saveAndFlush(newDeal);

        return newDeal;
    }

    @Override
    public Deal update(Long id, DealInput input) {
        Deal existingDeal =  customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        Deal updatedDeal = customerMappers.inputToUpdatedEntity(existingDeal, input);
        fetchRelationships(existingDeal, input);

        customerRepository.saveAndFlush(updatedDeal);

        return updatedDeal;
    }

    @Override
    public void delete(Long id) {
        boolean isDealExisting = customerRepository.existsById(id);
        if (!isDealExisting) {
            throw new EntityNotFoundException("customer not found");
        }

        customerRepository.deleteById(id);
    }

    @Override
    public void validate(DealInput input) {
        if (input.endDate() != null && input.endDate().isBefore(input.startDate())) {
            throw new BusinessLogicException("End date must be after the start date.");
        }

        boolean isStatusSignedOrPaid = isStatusSignedOrPaid(input);
        if (isStatusSignedOrPaid && (input.finalAmount() == null || input.finalAmount().compareTo(BigDecimal.ZERO) <= 0)) {
            throw new BusinessLogicException("Final amount must be greater than zero for SIGNED or PAID deals.");
        }

        if (input.dealStatus() == DealStatus.COMPLETED && (input.deliverables() == null || input.deliverables().isBlank())) {
            throw new BusinessLogicException("Deliverables must be provided for COMPLETED deals.");
        }

        if (isStatusSignedOrPaid && (input.terms() == null || input.terms().isBlank())) {
            throw new BusinessLogicException("Terms must be provided for SIGNED or PAID deals.");
        }

        if (input.dealStatus() == DealStatus.COMPLETED) {
            if (input.startDate() == null || input.endDate() == null) {
                throw new BusinessLogicException("Both start date and end date must be defined for COMPLETED deals.");
            }

            if (input.endDate().isBefore(input.startDate())) {
                throw new BusinessLogicException("End date must be after the start date for COMPLETED deals.");
            }
        }

        if ((input.dealStatus() == DealStatus.PAID || input.dealStatus() == DealStatus.COMPLETED)
                && input.finalAmount() != null && input.finalAmount().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Final amount cannot be zero for PAID or COMPLETED deals.");
        }
    }

    private boolean isStatusSignedOrPaid(DealInput input) {
        return input.dealStatus() == DealStatus.SIGNED || input.dealStatus() == DealStatus.PAID;
    }


    private void fetchRelationships(Deal deal, DealInput input) {
        if (input.campaignManagerId() != null) {
            User user = userRepository.findById(input.campaignManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            deal.setCampaignManager(user);
        }

        Opportunity opportunity = opportunityRepository.findById(input.opportunityId())
                .orElseThrow(() -> new EntityNotFoundException("opportunity not found"));
        deal.setOpportunity(opportunity);
        deal.setCustomer(opportunity.getCustomer());

        List<ServicePackage> servicePackages = servicePackageRepository.findAllById(input.servicePackageIds());

        deal.setServices(servicePackages);
    }
}
