package at.backend.CRM.Controller;

import at.backend.CRM.Inputs.PageInput;
import at.backend.CRM.Inputs.CustomerInput;
import at.backend.CRM.Models.Customer;
import at.backend.CRM.Service.CommonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CommonService<Customer, CustomerInput> service;

    @QueryMapping
    public Page<Customer> getAllCustomers(@Argument PageInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.size());

        return service.findAll(pageable);
    }

    @QueryMapping
    public Customer getCustomerById(@Argument Long id) {
        return service.findById(id)
                .orElse(new Customer());
    }

    @MutationMapping
    public Customer createCustomer(@Valid @Argument CustomerInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public Customer updateCustomer(@Valid @Argument Long id, @Argument CustomerInput input) {
        service.validate(input);

        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteCustomer(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
