package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.UserInput;
import at.backend.CRM.Models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T12:29:38-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class UserMappersImpl implements UserMappers {

    @Override
    public User inputToEntity(UserInput input) {
        if ( input == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( input.username() );
        user.setEmail( input.email() );
        user.setRole( input.role() );

        return user;
    }

    @Override
    public User inputToUpdatedEntity(User existingUser, UserInput input) {
        if ( input == null ) {
            return existingUser;
        }

        existingUser.setUsername( input.username() );
        existingUser.setEmail( input.email() );
        existingUser.setPassword( input.password() );
        existingUser.setRole( input.role() );

        return existingUser;
    }
}
