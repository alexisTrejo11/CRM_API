package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.DealInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DealMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Deal createInputToEntity(DealInput input);

    Deal updateInputToEntity(@MappingTarget Deal existingDeal, DealInput input);
         
}
