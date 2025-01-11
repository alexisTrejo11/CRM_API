package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.ActivityInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ActivityMappers {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedUser", ignore = true)
    @Mapping(target = "deal", ignore = true)
    @Mapping(target = "contact", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Activity createInputToEntity(ActivityInput input);

    Activity updateInputToEntity(@MappingTarget Activity existingActivity, ActivityInput input);

    }
