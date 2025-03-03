package at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers;

import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarketingCampaignMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    MarketingCampaignModel inputToEntity(MarketingCampaignInsertDTO input);


    MarketingCampaignDTO entityToDTO(MarketingCampaignModel entity);

    void updateEntity(@MappingTarget MarketingCampaignModel entity, MarketingCampaignInsertDTO input);
}

