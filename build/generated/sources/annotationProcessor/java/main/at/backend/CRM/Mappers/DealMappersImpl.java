package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.DealInput;
import at.backend.CRM.Models.Deal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T15:49:04-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class DealMappersImpl implements DealMappers {

    @Override
    public Deal inputToEntity(DealInput input) {
        if ( input == null ) {
            return null;
        }

        Deal deal = new Deal();

        deal.setDealStatus( input.dealStatus() );
        deal.setFinalAmount( input.finalAmount() );
        deal.setStartDate( input.startDate() );
        deal.setEndDate( input.endDate() );
        deal.setDeliverables( input.deliverables() );
        deal.setTerms( input.terms() );

        return deal;
    }

    @Override
    public Deal inputToUpdatedEntity(Deal existingUser, DealInput input) {
        if ( input == null ) {
            return existingUser;
        }

        existingUser.setDealStatus( input.dealStatus() );
        existingUser.setFinalAmount( input.finalAmount() );
        existingUser.setStartDate( input.startDate() );
        existingUser.setEndDate( input.endDate() );
        existingUser.setDeliverables( input.deliverables() );
        existingUser.setTerms( input.terms() );

        return existingUser;
    }
}
