package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.UserInput;
import at.backend.CRM.Models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-10T17:29:26-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class UserMappersImpl implements UserMappers {

    @Override
    public User createInputToEntity(UserInput input) {
        if ( input == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( input.firstName() );
        user.setLastName( input.lastName() );
        user.setEmail( input.email() );
        user.setPassword( input.password() );
        user.setRole( input.role() );

        return user;
    }

    @Override
    public User updateInputToEntity(User existingUser, UserInput input) {
        if ( input == null ) {
            return existingUser;
        }

        existingUser.setFirstName( input.firstName() );
        existingUser.setLastName( input.lastName() );
        existingUser.setEmail( input.email() );
        existingUser.setPassword( input.password() );
        existingUser.setRole( input.role() );

        return existingUser;
    }
}
