package at.backend.MarketingProject.AutoMappers;

import at.backend.MarketingProject.DTOs.CampaignMetricDTO;
import at.backend.MarketingProject.DTOs.CampaignMetricInsertDTO;
import at.backend.MarketingProject.Models.CampaignMetric;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CampaignMetricMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CampaignMetric inputToEntity(CampaignMetricInsertDTO input);


    CampaignMetricDTO entityToDTO(CampaignMetric entity);

    void updateEntity(@MappingTarget  CampaignMetric entity, CampaignMetricInsertDTO input);
}

