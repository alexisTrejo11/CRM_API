package at.backend.CRM.CRM.Mappers;

import at.backend.CRM.CRM.Inputs.QuoteInput;
import at.backend.CRM.CRM.Models.Quote;
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
