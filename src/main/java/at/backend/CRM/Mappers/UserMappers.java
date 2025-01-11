package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.UserInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMappers {

    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    User createInputToEntity(UserInput input);

    User updateInputToEntity(@MappingTarget User existingUser, UserInput input);
         
}
