package at.backend.CRM.Service;

import at.backend.CRM.Inputs.ContactInput;
import at.backend.CRM.Mappers.ContactMappers;
import at.backend.CRM.Models.Company;
import at.backend.CRM.Models.Contact;
import at.backend.CRM.Repository.CompanyRepository;
import at.backend.CRM.Repository.ContactRepository;
import at.backend.CRM.Utils.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements CommonService<Contact, ContactInput> {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final ContactMappers companyMapper;

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact create(ContactInput input) {
        Contact contact = companyMapper.createInputToEntity(input);

        Company company = companyRepository.findById(input.companyId())
                .orElseThrow(() -> new EntityNotFoundException("Company Not Found"));
        contact.setCompany(company);

        contactRepository.saveAndFlush(contact);

        return contact;
    }

    @Override
    public Contact update(Long id, ContactInput input) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Contact not found"));

        Contact companyUpdated = companyMapper.updateInputToEntity(existingContact, input);
        contactRepository.saveAndFlush(companyUpdated);

        return companyUpdated;
    }

    @Override
    public void delete(Long id) {
        boolean isContactExisting = contactRepository.existsById(id);
        if (!isContactExisting) {
            throw new EntityNotFoundException("Contact not found");
        }

        contactRepository.deleteById(id);
    }

    @Override
    public void validate(ContactInput input) {
    }
}
