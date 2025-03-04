package at.backend.MarketingCompany.marketing.attribution.infrastructure.automappers;

import at.backend.MarketingCompany.marketing.attribution.domain.CampaignAttribution;
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
    CampaignAttribution insertDTOToDomain(CampaignAttributionInsertDTO insertDTO);

    @Mapping(target = "campaignId", source = "campaign.id")
    @Mapping(target = "dealId", source = "deal.id")
    CampaignAttribution dtoToDomain(CampaignAttributionDTO domain);

    CampaignAttributionModel domainToModel(CampaignAttribution domain);
    CampaignAttributionDTO domainToDTO(CampaignAttribution domain);


    CampaignAttributionDTO modelToDTO(CampaignAttributionModel model);
    CampaignAttribution modelToDomain(CampaignAttributionModel domain);


    void updateDomain(@MappingTarget CampaignAttribution entity, CampaignAttributionInsertDTO input);
}

