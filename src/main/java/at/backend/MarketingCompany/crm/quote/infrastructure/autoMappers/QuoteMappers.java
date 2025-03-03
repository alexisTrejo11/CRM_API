package at.backend.MarketingCompany.crm.quote.infrastructure.autoMappers;

import at.backend.MarketingCompany.crm.quote.domain.Quote;
import at.backend.MarketingCompany.crm.quote.infrastructure.DTOs.QuoteInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuoteMappers {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Quote inputToEntity(QuoteInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Quote inputToUpdatedEntity(@MappingTarget Quote existingQuote, QuoteInput input);
         
}
