package at.backend.MarketingProject.AutoMappers;

import at.backend.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingProject.Models.CampaignInteraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Mapper(componentModel = "spring")
public interface CampaignInteractionMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CampaignInteraction inputToEntity(CampaignInteractionInsertDTO input);

    CampaignInteractionDTO entityToDTO(CampaignInteraction entity);

    void updateEntity(@ModelAttribute CampaignInteraction entity, CampaignInteractionInsertDTO input);
}

