package at.backend.MarketingCompany.crm.deal.api.service;

import at.backend.MarketingCompany.common.exceptions.BusinessLogicException;
import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.crm.Utils.enums.DealStatus;
import at.backend.MarketingCompany.crm.deal.api.repository.DealRepository;
import at.backend.MarketingCompany.crm.deal.domain.Deal;
import at.backend.MarketingCompany.crm.deal.infrastructure.DTOs.DealInput;
import at.backend.MarketingCompany.crm.deal.infrastructure.autoMappers.DealMappers;
import at.backend.MarketingCompany.crm.opportunity.api.repository.OpportunityRepository;
import at.backend.MarketingCompany.crm.opportunity.domain.Opportunity;
import at.backend.MarketingCompany.crm.servicePackage.api.repostiory.ServicePackageRepository;
import at.backend.MarketingCompany.crm.servicePackage.domain.ServicePackage;
import at.backend.MarketingCompany.customer.User;
import at.backend.MarketingCompany.customer.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements CommonService<Deal, DealInput, UUID> {

    public final DealRepository customerRepository;
    public final DealMappers customerMappers;
    public final ServicePackageRepository servicePackageRepository;
    public final UserRepository userRepository;
    public final OpportunityRepository opportunityRepository;

    @Override
    public Page<Deal> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Deal getById(UUID id) {
        return getDeal(id);
    }

    @Override
    public Deal create(DealInput input) {
        Deal newDeal = customerMappers.inputToEntity(input);

        fetchRelationships(newDeal, input);
        customerRepository.saveAndFlush(newDeal);

        return newDeal;
    }

    @Override
    public Deal update(UUID id, DealInput input) {
        Deal existingDeal = getDeal(id);

        Deal updatedDeal = customerMappers.inputToUpdatedEntity(existingDeal, input);
        fetchRelationships(existingDeal, input);

        customerRepository.saveAndFlush(updatedDeal);

        return updatedDeal;
    }

    @Override
    public void delete(UUID id) {
        Deal deal = getDeal(id);

        customerRepository.delete(deal);
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

    private Deal getDeal(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("deal not found"));
    }
}