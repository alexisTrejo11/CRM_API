package at.backend.MarketingCompany.customer.api.service;

import at.backend.MarketingCompany.customer.api.repository.CustomerModel;
import at.backend.MarketingCompany.customer.domain.Customer;
import at.backend.MarketingCompany.customer.domain.ValueObjects.BusinessProfile;
import at.backend.MarketingCompany.customer.domain.ValueObjects.ContactDetails;
import at.backend.MarketingCompany.customer.domain.ValueObjects.PersonalInfo;
import at.backend.MarketingCompany.customer.api.repository.CustomerRepository;
import at.backend.MarketingCompany.customer.infrastructure.CustomerDTO;
import at.backend.MarketingCompany.customer.infrastructure.CustomerInsertDTO;
import at.backend.MarketingCompany.customer.infrastructure.CustomerMappers;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getById(UUID id) {
        return CustomerMappers.domainToDTO(findDomainById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(CustomerMappers::modelToDTO);
    }

    @Override
    @Transactional
    public CustomerDTO create(CustomerInsertDTO dto) {
        Customer domain = CustomerMappers.insertDTOToDomain(dto);
        domain.validate();

        CustomerModel model = CustomerMappers.domainToModel(domain);
        repository.save(model);

        return CustomerMappers.domainToDTO(domain);
    }

    @Override
    @Transactional
    public CustomerDTO update(UUID id, CustomerInsertDTO dto) {
        Customer existing = findDomainById(id);

        if (dto.getFirstName() != null || dto.getLastName() != null) {
            existing.updatePersonalInfo(PersonalInfo.builder()
                    .firstName(dto.getFirstName() != null ? dto.getFirstName() : existing.getPersonalInfo().getFirstName())
                    .lastName(dto.getLastName() != null ? dto.getLastName() : existing.getPersonalInfo().getLastName())
                    .build());
        }

        if (dto.getEmail() != null || dto.getPhone() != null) {
            existing.updateContactDetails(ContactDetails.builder()
                    .email(dto.getEmail() != null ? dto.getEmail() : existing.getContactDetails().getEmail())
                    .phone(dto.getPhone() != null ? dto.getPhone() : existing.getContactDetails().getPhone())
                    .build());
        }

        if (hasBusinessProfileChanges(dto, existing)) {
            BusinessProfile existingProfile = existing.getBusinessProfile();
            BusinessProfile newProfile = BusinessProfile.builder()
                    .company(dto.getCompany() != null ? dto.getCompany() : Optional.ofNullable(existingProfile).map(BusinessProfile::getCompany).orElse(null))
                    .industry(dto.getIndustry() != null ? dto.getIndustry() : Optional.ofNullable(existingProfile).map(BusinessProfile::getIndustry).orElse(null))
                    .brandVoice(dto.getBrandVoice() != null ? dto.getBrandVoice() : Optional.ofNullable(existingProfile).map(BusinessProfile::getBrandVoice).orElse(null))
                    .brandColors(dto.getBrandColors() != null ? dto.getBrandColors() : Optional.ofNullable(existingProfile).map(BusinessProfile::getBrandColors).orElse(null))
                    .targetMarket(dto.getTargetMarket() != null ? dto.getTargetMarket() : Optional.ofNullable(existingProfile).map(BusinessProfile::getTargetMarket).orElse(null))
                    .competitorUrls(dto.getCompetitorUrls() != null ? dto.getCompetitorUrls() : Optional.ofNullable(existingProfile).map(BusinessProfile::getCompetitorUrls).orElse(null))
                    .socialMediaHandles(dto.getSocialMediaHandles() != null ? dto.getSocialMediaHandles() : Optional.ofNullable(existingProfile).map(BusinessProfile::getSocialMediaHandles).orElse(null))
                    .build();
            existing.updateBusinessProfile(newProfile);
        }

        CustomerModel updatedModel = CustomerMappers.domainToModel(existing);
        repository.save(updatedModel);

        return CustomerMappers.domainToDTO(existing);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Customer domain = findDomainById(id);
        checkDeletionRules(domain);
        repository.deleteById(id);
    }

    @Override
    public void validate(CustomerInsertDTO input) {

    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBlocked(UUID id) {
        Customer customer = findDomainById(id);
        return customer.isBlocked();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasSocialMediaHandles(UUID id) {
        Customer customer = findDomainById(id);
        return Optional.ofNullable(customer.getBusinessProfile())
                .map(BusinessProfile::hasSocialMediaHandles)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasCompetitors(UUID id) {
        Customer customer = findDomainById(id);
        return Optional.ofNullable(customer.getBusinessProfile())
                .map(BusinessProfile::hasCompetitors)
                .orElse(false);
    }

    private Customer findDomainById(UUID id) {
        return repository.findById(id)
                .map(CustomerMappers::modelToDomain)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    private boolean hasBusinessProfileChanges(CustomerInsertDTO dto, Customer existing) {
        BusinessProfile current = existing.getBusinessProfile();
        return dto.getCompany() != null ||
                dto.getIndustry() != null ||
                dto.getBrandVoice() != null ||
                dto.getBrandColors() != null ||
                dto.getTargetMarket() != null ||
                dto.getCompetitorUrls() != null ||
                dto.getSocialMediaHandles() != null;
    }

    private void checkDeletionRules(Customer domain) {
        if (domain.isBlocked()) {
            throw new IllegalStateException("Cannot delete blocked customers");
        }
        if (!domain.getOpportunities().isEmpty()) {
            throw new IllegalStateException("Cannot delete customer with active opportunities");
        }
        if (!domain.getInteractions().isEmpty()) {
            throw new IllegalStateException("Cannot delete customer with pending interactions");
        }
    }
}