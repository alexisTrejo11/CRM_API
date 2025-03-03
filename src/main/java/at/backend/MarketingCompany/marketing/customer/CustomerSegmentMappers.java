package at.backend.MarketingCompany.marketing.customer;

import at.backend.MarketingCompany.marketing.interaction.api.repository.CustomerSegment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerSegmentMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CustomerSegment inputToEntity(CustomerSegmentInsertDTO input);


    CustomerSegmentDTO entityToDTO(CustomerSegment entity);

    void updateEntity(@MappingTarget CustomerSegment entity, CustomerSegmentInsertDTO input);

}

