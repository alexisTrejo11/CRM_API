package at.backend.CRM.MarketingProject.AutoMappers;

import at.backend.CRM.MarketingProject.DTOs.CustomerSegmentDTO;
import at.backend.CRM.MarketingProject.DTOs.CustomerSegmentInsertDTO;
import at.backend.CRM.MarketingProject.Models.CustomerSegment;
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

