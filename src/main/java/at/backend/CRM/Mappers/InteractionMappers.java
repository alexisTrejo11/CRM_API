package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.InteractionInput;
import at.backend.CRM.Models.Interaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InteractionMappers {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Interaction inputToEntity(InteractionInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Interaction inputToUpdatedEntity(@MappingTarget Interaction existingInteraction, InteractionInput input);

}
