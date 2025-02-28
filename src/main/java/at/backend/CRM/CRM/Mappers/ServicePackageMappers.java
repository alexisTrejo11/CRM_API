package at.backend.CRM.CRM.Mappers;

import at.backend.CRM.CRM.Inputs.ServicePackageInput;
import at.backend.CRM.CRM.Models.ServicePackage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServicePackageMappers {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ServicePackage inputToEntity(ServicePackageInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ServicePackage inputToUpdatedEntity(@MappingTarget ServicePackage existingUser, ServicePackageInput input);
         
}
