package at.backend.CRM.MarketingProject.AutoMappers;

import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignInteraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CampaignInteractionMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CampaignInteraction inputToEntity(CampaignInteractionInsertDTO input);

    CampaignInteractionDTO entityToDTO(CampaignInteraction entity);

    void updateEntity(@MappingTarget CampaignInteraction entity, CampaignInteractionInsertDTO input);
}

