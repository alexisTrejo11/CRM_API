package at.backend.MarketingCompany.crm.opportunity.infrastructure.autoMappers;

import at.backend.MarketingCompany.crm.opportunity.infrastructure.DTOs.OpportunityInput;
import at.backend.MarketingCompany.crm.opportunity.domain.Opportunity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OpportunityMappers {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Opportunity inputToEntity(OpportunityInput input);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Opportunity inputToUpdatedEntity(@MappingTarget Opportunity existingUser, OpportunityInput input);
         
}
