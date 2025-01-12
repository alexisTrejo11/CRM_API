package at.backend.CRM.Service;

import at.backend.CRM.Inputs.CustomerInput;
import at.backend.CRM.Mappers.CustomerMappers;
import at.backend.CRM.Models.Customer;
import at.backend.CRM.Repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements  CommonService<Customer, CustomerInput>{

    public final CustomerRepository customerRepository;
    public final CustomerMappers customerMappers;
    public final FieldValidationService fieldValidationService;

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer create(CustomerInput input) {
        Customer newUser = customerMappers.inputToEntity(input);

        customerRepository.saveAndFlush(newUser);

        return newUser;
    }

    @Override
    public Customer update(Long id, CustomerInput input) {
        Customer existingCustomer =  customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        Customer updatedUser = customerMappers.inputToUpdatedEntity(existingCustomer, input);

        customerRepository.saveAndFlush(updatedUser);

        return updatedUser;
    }

    @Override
    public void delete(Long id) {
        boolean isCustomerExisting = customerRepository.existsById(id);
        if (!isCustomerExisting) {
            throw new EntityNotFoundException("customer not found");
        }

        customerRepository.deleteById(id);
    }

    @Override
    public void validate(CustomerInput input) {
        fieldValidationService.validateEmail(input.email());
        fieldValidationService.validatePhone(input.phone());
    }
}
