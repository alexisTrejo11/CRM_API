package at.backend.MarketingProject.AutoMappers;

import at.backend.MarketingProject.DTOs.MarketingCampaignDTO;
import at.backend.MarketingProject.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingProject.Models.MarketingCampaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarketingCampaignMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    MarketingCampaign inputToEntity(MarketingCampaignInsertDTO input);


    @Mapping(target = "id", ignore = true)
    MarketingCampaignDTO entityToDTO(MarketingCampaign entity);

    void updateEntity(@MappingTarget MarketingCampaign entity, MarketingCampaignInsertDTO input);
}

