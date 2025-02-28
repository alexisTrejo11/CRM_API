package at.backend.CRM.MarketingProject.AutoMappers;

import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignAttribution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CampaignAttributionMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CampaignAttribution inputToEntity(CampaignAttributionInsertDTO input);

    CampaignAttributionDTO entityToDTO(CampaignAttribution entity);
    void updateEntity(@MappingTarget CampaignAttribution entity, CampaignAttributionInsertDTO input);
}

