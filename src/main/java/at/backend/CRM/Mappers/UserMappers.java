package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.UserInput;
import at.backend.CRM.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    User inputToEntity(UserInput input);

    User inputToUpdatedEntity(@MappingTarget User existingUser, UserInput input);
         
}
