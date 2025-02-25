package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.OpportunityInput;
import at.backend.CRM.Models.Opportunity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T15:49:04-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class OpportunityMappersImpl implements OpportunityMappers {

    @Override
    public Opportunity inputToEntity(OpportunityInput input) {
        if ( input == null ) {
            return null;
        }

        Opportunity opportunity = new Opportunity();

        opportunity.setTitle( input.title() );
        opportunity.setAmount( input.amount() );
        opportunity.setStage( input.stage() );
        opportunity.setExpectedCloseDate( input.expectedCloseDate() );

        return opportunity;
    }

    @Override
    public Opportunity inputToUpdatedEntity(Opportunity existingUser, OpportunityInput input) {
        if ( input == null ) {
            return existingUser;
        }

        existingUser.setTitle( input.title() );
        existingUser.setAmount( input.amount() );
        existingUser.setStage( input.stage() );
        existingUser.setExpectedCloseDate( input.expectedCloseDate() );

        return existingUser;
    }
}
