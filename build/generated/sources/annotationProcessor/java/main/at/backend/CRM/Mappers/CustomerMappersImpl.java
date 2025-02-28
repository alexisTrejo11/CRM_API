package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.CustomerInput;
import at.backend.CRM.Models.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-28T14:49:06-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CustomerMappersImpl implements CustomerMappers {

    @Override
    public Customer inputToEntity(CustomerInput input) {
        if ( input == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setFirstName( input.firstName() );
        customer.setLastName( input.lastName() );
        customer.setEmail( input.email() );
        customer.setPhone( input.phone() );
        customer.setCompany( input.company() );

        return customer;
    }

    @Override
    public Customer inputToUpdatedEntity(Customer existingUser, CustomerInput input) {
        if ( input == null ) {
            return existingUser;
        }

        existingUser.setFirstName( input.firstName() );
        existingUser.setLastName( input.lastName() );
        existingUser.setEmail( input.email() );
        existingUser.setPhone( input.phone() );
        existingUser.setCompany( input.company() );

        return existingUser;
    }
}
