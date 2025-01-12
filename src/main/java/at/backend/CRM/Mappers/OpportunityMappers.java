package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.OpportunityInput;
import at.backend.CRM.Models.Opportunity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OpportunityMappers {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Opportunity inputToEntity(OpportunityInput input);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Opportunity inputToUpdatedEntity(@MappingTarget Opportunity existingUser, OpportunityInput input);
         
}
