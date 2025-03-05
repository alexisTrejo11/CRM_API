package at.backend.MarketingCompany.user.infrastructure;

import at.backend.MarketingCompany.user.api.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    User inputToEntity(UserInput input);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User inputToUpdatedEntity(@MappingTarget User existingUser, UserInput input);
         
}
