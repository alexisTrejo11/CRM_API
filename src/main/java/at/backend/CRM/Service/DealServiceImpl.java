package at.backend.CRM.Service;

import at.backend.CRM.Inputs.DealInput;
import at.backend.CRM.Mappers.DealMappers;
import at.backend.CRM.Models.Company;
import at.backend.CRM.Models.Contact;
import at.backend.CRM.Models.Deal;
import at.backend.CRM.Repository.CompanyRepository;
import at.backend.CRM.Repository.ContactRepository;
import at.backend.CRM.Repository.DealRepository;
import at.backend.CRM.Utils.BusinessLogicException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements CommonService<Deal, DealInput> {

    private final DealRepository dealRepository;
    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;
    private final DealMappers companyMapper;

    @Override
    public List<Deal> findAll() {
        return dealRepository.findAll();
    }

    @Override
    public Optional<Deal> findById(Long id) {
        return dealRepository.findById(id);
    }

    @Override
    public Deal create(DealInput input) {
        Deal deal = companyMapper.createInputToEntity(input);

        if (input.companyId() != null) {
            deal.setCompany(getValidCompany(input.companyId()));
        }
        if (input.contactIds() != null) {
            deal.setContacts(getValidContacts(input.contactIds()));
        }

        dealRepository.saveAndFlush(deal);

        return deal;
    }

    @Override
    public Deal update(Long id, DealInput input) {
        Deal existingDeal = dealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Deal not found with ID: " + id));

        Deal updatedDeal = companyMapper.updateInputToEntity(existingDeal, input);

        if (input.companyId() != null) {
            updatedDeal.setCompany(getValidCompany(input.companyId()));
        }
        if (input.contactIds() != null) {
            updatedDeal.setContacts(getValidContacts(input.contactIds()));
        }

        dealRepository.saveAndFlush(updatedDeal);
        return updatedDeal;
    }

    @Override
    public void delete(Long id) {
        if (!dealRepository.existsById(id)) {
            throw new EntityNotFoundException("Deal not found");
        }
        dealRepository.deleteById(id);
    }

    @Override
    public void validate(DealInput input) {

        if (input.companyId() != null) {

            Company company = getValidCompany(input.companyId());
            if (input.contactIds() != null) {

                System.out.println(company.getId());
                List<Contact> contacts = new ArrayList<>(getValidContacts(input.contactIds()));

                for (Contact contact : contacts) {
                    if (!contact.getCompany().getId().equals(company.getId())) {
                        throw new BusinessLogicException("All contacts must belong to the specified company.");
                    }
                }
            }
        }

        if (input.value() != null) {
            if (input.value().compareTo(BigDecimal.valueOf(1000000)) > 0) {
                throw new BusinessLogicException("The deal value exceeds the allowed maximum limit.");
            }
        }

        if (input.expectedCloseDate() != null) {
            if (input.expectedCloseDate().isBefore(LocalDateTime.now())) {
                throw new BusinessLogicException("The expected close date must be in the future.");
            }
        }


        //if (dealRepository.existsByName(input.name())) {
        //    throw new BusinessLogicException("A deal with the same name already exists. Please choose a unique name.");
        //}
    }


    private Company getValidCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + companyId));
    }

    @Transactional
    private Set<Contact> getValidContacts(Set<Long> contactIds) {
        List<Contact> contactsFromDb = contactRepository.findAllById(contactIds);


        if (contactsFromDb.size() != contactIds.size()) {
            throw new EntityNotFoundException("One or more contacts not found with the provided IDs.");
        }

        return new HashSet<>(contactsFromDb);
    }

}

