package at.backend.CRM.CRM.Service;

import at.backend.CRM.CRM.Inputs.CustomerInput;
import at.backend.CRM.CRM.Mappers.CustomerMappers;
import at.backend.CRM.CRM.Models.Customer;
import at.backend.CRM.CRM.Models.User;
import at.backend.CRM.CRM.Repository.CustomerRepository;
import at.backend.CRM.CommonClasses.Service.CommonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Customer getById(Long id) {
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
    public void delete(Long id) {
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
