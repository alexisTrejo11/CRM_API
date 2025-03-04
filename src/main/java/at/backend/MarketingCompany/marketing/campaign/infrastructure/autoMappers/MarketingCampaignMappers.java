package at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers;

import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.SuccessCriteria;
import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.TargetAudience;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.SuccessCriteriaDetailsDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.TargetAudienceDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarketingCampaignMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    MarketingCampaign inputToDomain(MarketingCampaignInsertDTO input);

    MarketingCampaignModel domainToModel(MarketingCampaign campaign);
    MarketingCampaign modelToDomain(MarketingCampaignModel model);
    MarketingCampaignDTO modelToDTO(MarketingCampaignModel model);
    MarketingCampaignDTO domainToDTO(MarketingCampaign entity);

    TargetAudience toTargetAudience(TargetAudienceDetailsDTO audienceDetailsDTO);
    SuccessCriteria toSuccessCriteria(SuccessCriteriaDetailsDTO criteriaDetailsDTO);

    void updateEntity(@MappingTarget MarketingCampaign domain, MarketingCampaignInsertDTO input);
}

