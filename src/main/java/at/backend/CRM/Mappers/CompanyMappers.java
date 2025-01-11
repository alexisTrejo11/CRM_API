package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.CompanyInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompanyMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Company createInputToEntity(CompanyInput input);

    Company updateInputToEntity(@MappingTarget Company existingCompany, CompanyInput input);

}
