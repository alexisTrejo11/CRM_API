package at.backend.MarketingCompany.customer.api.service;

import at.backend.MarketingCompany.customer.infrastructure.CustomerInput;
import at.backend.MarketingCompany.common.service.FieldValidationService;
import at.backend.MarketingCompany.customer.infrastructure.CustomerMappers;
import at.backend.MarketingCompany.customer.domain.Customer;
import at.backend.MarketingCompany.customer.api.repository.CustomerRepository;
import at.backend.MarketingCompany.common.service.CommonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CommonService<Customer, CustomerInput> {

    public final CustomerRepository customerRepository;
    public final CustomerMappers customerMappers;
    public final FieldValidationService fieldValidationService;

    @Override
    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getById(Object id) {
        return getCustomer(id);
    }

    @Override
    public Customer create(CustomerInput input) {
        Customer newUser = customerMappers.inputToEntity(input);

        customerRepository.saveAndFlush(newUser);

        return newUser;
    }

    @Override
    public Customer update(Long id, CustomerInput input) {
        Customer existingCustomer = getCustomer(id);

        Customer updatedUser = customerMappers.inputToUpdatedEntity(existingCustomer, input);

        customerRepository.saveAndFlush(updatedUser);

        return updatedUser;
    }

    @Override
    public void delete(Object id) {
        Customer customer = getCustomer(id);

        customerRepository.delete(customer);
    }

    @Override
    public void validate(CustomerInput input) {
        fieldValidationService.validateEmail(input.email());
        fieldValidationService.validatePhone(input.phone());
    }

    private Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
    }
}
