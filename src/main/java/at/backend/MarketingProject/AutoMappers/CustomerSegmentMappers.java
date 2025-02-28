package at.backend.MarketingProject.AutoMappers;

import at.backend.MarketingProject.DTOs.*;
import at.backend.MarketingProject.Models.CustomerSegment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerSegmentMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CustomerSegment inputToEntity(CustomerSegmentInsertDTO input);


    @Mapping(target = "id", ignore = true)
    CustomerSegmentDTO entityToDTO(CustomerSegment entity);

    void updateEntity(@MappingTarget CustomerSegment entity, CustomerSegmentInsertDTO input);

}

