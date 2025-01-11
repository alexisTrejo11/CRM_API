package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.CompanyInput;
import at.backend.CRM.Models.Company;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-10T17:29:26-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class CompanyMappersImpl implements CompanyMappers {

    @Override
    public Company createInputToEntity(CompanyInput input) {
        if ( input == null ) {
            return null;
        }

        Company.CompanyBuilder company = Company.builder();

        company.name( input.name() );
        company.taxNumber( input.taxNumber() );
        company.industry( input.industry() );
        company.website( input.website() );

        return company.build();
    }

    @Override
    public Company updateInputToEntity(Company existingCompany, CompanyInput input) {
        if ( input == null ) {
            return existingCompany;
        }

        existingCompany.setName( input.name() );
        existingCompany.setTaxNumber( input.taxNumber() );
        existingCompany.setIndustry( input.industry() );
        existingCompany.setWebsite( input.website() );

        return existingCompany;
    }
}
