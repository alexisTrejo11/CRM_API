package at.backend.MarketingCompany.customer.infrastructure;

import at.backend.MarketingCompany.customer.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "opportunities", ignore = true)
    @Mapping(target = "interactions", ignore = true)
    Customer inputToEntity(CustomerInput input);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "opportunities", ignore = true)
    @Mapping(target = "interactions", ignore = true)
    Customer inputToUpdatedEntity(@MappingTarget Customer existingUser, CustomerInput input);
         
}
