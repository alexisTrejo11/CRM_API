package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.QuoteInput;
import at.backend.CRM.Inputs.QuoteItemInput;
import at.backend.CRM.Models.Quote;
import at.backend.CRM.Models.QuoteItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuoteItemMappers {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "opportunity", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    QuoteItem inputToEntity(QuoteItemInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "opportunity", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    QuoteItem inputToUpdatedEntity(@MappingTarget QuoteItemInput existingItem, QuoteItemInput input);
         
}
