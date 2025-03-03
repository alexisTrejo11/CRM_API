package at.backend.MarketingCompany.crm.servicePackage.infrastructure.autoMappers;

import at.backend.MarketingCompany.crm.servicePackage.infrastructure.DTOs.ServicePackageInput;
import at.backend.MarketingCompany.crm.servicePackage.domain.ServicePackage;
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
