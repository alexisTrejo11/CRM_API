package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.ContactInput;
import at.backend.CRM.Models.Contact;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-10T16:13:21-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class ContactMappersImpl implements ContactMappers {

    @Override
    public Contact createInputToEntity(ContactInput input) {
        if ( input == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setFirstName( input.firstName() );
        contact.setLastName( input.lastName() );
        contact.setEmail( input.email() );
        contact.setPhone( input.phone() );
        contact.setPosition( input.position() );

        return contact;
    }

    @Override
    public Contact updateInputToEntity(Contact existingContact, ContactInput input) {
        if ( input == null ) {
            return existingContact;
        }

        existingContact.setFirstName( input.firstName() );
        existingContact.setLastName( input.lastName() );
        existingContact.setEmail( input.email() );
        existingContact.setPhone( input.phone() );
        existingContact.setPosition( input.position() );

        return existingContact;
    }
}
