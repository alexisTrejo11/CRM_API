package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.DealInput;
import at.backend.CRM.Models.Deal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-11T15:44:56-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class DealMappersImpl implements DealMappers {

    @Override
    public Deal createInputToEntity(DealInput input) {
        if ( input == null ) {
            return null;
        }

        Deal deal = new Deal();

        deal.setName( input.name() );
        deal.setValue( input.value() );
        deal.setStage( input.stage() );
        deal.setExpectedCloseDate( input.expectedCloseDate() );

        return deal;
    }

    @Override
    public Deal updateInputToEntity(Deal existingDeal, DealInput input) {
        if ( input == null ) {
            return existingDeal;
        }

        existingDeal.setName( input.name() );
        existingDeal.setValue( input.value() );
        existingDeal.setStage( input.stage() );
        existingDeal.setExpectedCloseDate( input.expectedCloseDate() );

        return existingDeal;
    }
}
