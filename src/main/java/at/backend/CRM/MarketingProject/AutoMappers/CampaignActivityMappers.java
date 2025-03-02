package at.backend.CRM.MarketingProject.AutoMappers;

import at.backend.CRM.MarketingProject.DTOs.CampaignActivityDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CampaignActivityMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CampaignActivity inputToEntity(CampaignActivityInsertDTO input);

    @Mapping(target = "assignedTo", source   = "assignedTo.id")
    CampaignActivityDTO entityToDTO(CampaignActivity entity);

    void updateEntity(@MappingTarget CampaignActivity existingActivity, CampaignActivityInsertDTO input);
}

