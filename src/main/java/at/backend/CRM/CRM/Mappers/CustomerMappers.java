package at.backend.CRM.CRM.Mappers;

import at.backend.CRM.CRM.Inputs.CustomerInput;
import at.backend.CRM.CRM.Models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "opportunities", ignore = true)
    @Mapping(target = "interactions", ignore = true)
    Customer inputToEntity(CustomerInput input);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "opportunities", ignore = true)
    @Mapping(target = "interactions", ignore = true)
    Customer inputToUpdatedEntity(@MappingTarget Customer existingUser, CustomerInput input);
         
}
