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
    User createInputToEntity(UserInput input);

    User updateInputToEntity(@MappingTarget User existingUser, UserInput input);
         
}
