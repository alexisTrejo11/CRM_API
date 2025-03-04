package at.backend.MarketingCompany.crm.deal.infrastructure.autoMappers;

import at.backend.MarketingCompany.crm.deal.domain.Deal;
import at.backend.MarketingCompany.crm.deal.infrastructure.DTOs.DealInput;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T15:19:10-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
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
