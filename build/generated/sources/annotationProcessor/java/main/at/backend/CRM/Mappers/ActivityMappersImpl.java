package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.ActivityInput;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-11T15:44:56-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class ActivityMappersImpl implements ActivityMappers {

    @Override
    public Activity createInputToEntity(ActivityInput input) {
        if ( input == null ) {
            return null;
        }

        Activity.ActivityBuilder activity = Activity.builder();

        activity.type( input.type() );
        activity.description( input.description() );
        activity.dueDate( input.dueDate() );
        activity.status( input.status() );

        return activity.build();
    }

    @Override
    public Activity updateInputToEntity(Activity existingActivity, ActivityInput input) {
        if ( input == null ) {
            return existingActivity;
        }

        existingActivity.setType( input.type() );
        existingActivity.setDescription( input.description() );
        existingActivity.setDueDate( input.dueDate() );
        existingActivity.setStatus( input.status() );

        return existingActivity;
    }
}
