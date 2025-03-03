package at.backend.MarketingCompany.marketing.activity.infrastructure.AutoMappers;

import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CampaignActivityMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CampaignActivityModel inputToEntity(CampaignActivityInsertDTO input);

    @Mapping(target = "assignedTo", source   = "assignedTo.id")
    CampaignActivityDTO entityToDTO(CampaignActivityModel entity);

    void updateEntity(@MappingTarget CampaignActivityModel existingActivity, CampaignActivityInsertDTO input);
}

