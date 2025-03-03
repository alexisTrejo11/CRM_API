package at.backend.MarketingCompany.crm.interaction.infrastructure.autoMappers;

import at.backend.MarketingCompany.crm.interaction.infrastructure.DTOs.InteractionInput;
import at.backend.MarketingCompany.crm.interaction.domain.Interaction;
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
