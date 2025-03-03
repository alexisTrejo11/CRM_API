package at.backend.MarketingCompany.marketing.metric.infrastructure.autoMappers;

import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricDTO;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricInsertDTO;
import at.backend.MarketingCompany.marketing.metric.api.repository.CampaignMetric;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CampaignMetricMappers {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    CampaignMetric inputToEntity(CampaignMetricInsertDTO input);

    @Mapping(target = "campaignId", source = "campaign.id")
    CampaignMetricDTO entityToDTO(CampaignMetric entity);

    void updateEntity(@MappingTarget CampaignMetric entity, CampaignMetricInsertDTO input);
}

