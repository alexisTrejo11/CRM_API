package at.backend.CRM.CRM.Mappers;

import at.backend.CRM.CRM.Inputs.DealInput;
import at.backend.CRM.CRM.Models.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DealMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "opportunity", ignore = true)
    @Mapping(target = "campaignManager", ignore = true)
    Deal inputToEntity(DealInput input);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "opportunity", ignore = true)
    @Mapping(target = "campaignManager", ignore = true)
    Deal inputToUpdatedEntity(@MappingTarget Deal existingUser, DealInput input);
         
}
