package at.backend.MarketingCompany.marketing.attribution.infrastructure.automappers;

import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionDTO;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionInsertDTO;
import at.backend.MarketingCompany.marketing.attribution.api.repository.CampaignAttributionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CampaignAttributionMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CampaignAttributionModel inputToEntity(CampaignAttributionInsertDTO input);

    @Mapping(target = "campaignId", source = "campaign.id")
    @Mapping(target = "dealId", source = "deal.id")
    CampaignAttributionDTO entityToDTO(CampaignAttributionModel entity);


    void updateEntity(@MappingTarget CampaignAttributionModel entity, CampaignAttributionInsertDTO input);
}

