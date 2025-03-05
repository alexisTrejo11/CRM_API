package at.backend.MarketingCompany.marketing.interaction.infrastructure.autoMappers;

import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionDTO;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingCompany.marketing.interaction.api.repository.CampaignInteractionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CampaignInteractionMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CampaignInteractionModel inputToEntity(CampaignInteractionInsertDTO input);

    @Mapping(target = "campaignId", source = "campaign.id")
    @Mapping(target = "customerId", source = "customer.id")
    CampaignInteractionDTO entityToDTO(CampaignInteractionModel entity);

    void updateEntity(@MappingTarget CampaignInteractionModel entity, CampaignInteractionInsertDTO input);
}

