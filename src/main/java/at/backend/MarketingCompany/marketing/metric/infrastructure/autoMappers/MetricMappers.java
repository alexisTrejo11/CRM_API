package at.backend.MarketingCompany.marketing.metric.infrastructure.autoMappers;

import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.CampaignPeriod;
import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.SuccessCriteria;
import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.TargetAudience;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.SuccessCriteriaDetailsDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.TargetAudienceDetailsDTO;
import at.backend.MarketingCompany.marketing.metric.api.repository.CampaignMetricModel;
import at.backend.MarketingCompany.marketing.metric.domain.CampaignMetric;
import at.backend.MarketingCompany.marketing.metric.domain.CampaignMetricId;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricDTO;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricInsertDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MetricMappers {

    public CampaignMetric insertDTOToDomain(CampaignMetricInsertDTO insertDTO) {
        return CampaignMetric.builder()
                .name(insertDTO.getName())
                .type(insertDTO.getType())
                .description(insertDTO.getDescription())
                .value(insertDTO.getValue())
                .targetValue(insertDTO.getTargetValue())
                .measurementUnit(insertDTO.getMeasurementUnit())
                .calculationFormula(insertDTO.getCalculationFormula())
                .dataSource(insertDTO.getDataSource())
                .automated(insertDTO.isAutomated())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public CampaignMetricDTO domainToDTO(CampaignMetric domain) {
        return CampaignMetricDTO.builder()
                .id(domain.getId().value())
                .campaignId(domain.getCampaign().getId().getValue())
                .name(domain.getName())
                .type(domain.getType())
                .description(domain.getDescription())
                .value(domain.getValue())
                .targetValue(domain.getTargetValue())
                .measurementUnit(domain.getMeasurementUnit())
                .lastCalculated(domain.getLastCalculated())
                .calculationFormula(domain.getCalculationFormula())
                .dataSource(domain.getDataSource())
                .automated(domain.isAutomated())
                .build();
    }

    public CampaignMetricModel domainToModel(CampaignMetric domain) {
        return CampaignMetricModel.builder()
                .id(domain.getId().value())
                //.campaign(new MarketingCampaignModel(domain.getCampaign().getId().value()))
                .name(domain.getName())
                .type(domain.getType())
                .description(domain.getDescription())
                .value(domain.getValue())
                .targetValue(domain.getTargetValue())
                .measurementUnit(domain.getMeasurementUnit())
                .lastCalculated(domain.getLastCalculated())
                .calculationFormula(domain.getCalculationFormula())
                .dataSource(domain.getDataSource())
                .automated(domain.isAutomated())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public CampaignMetricDTO modelToDTO(CampaignMetricModel model) {
        return CampaignMetricDTO.builder()
                .id(model.getId())
                .campaignId(model.getCampaign().getId())
                .name(model.getName())
                .type(model.getType())
                .description(model.getDescription())
                .value(model.getValue())
                .targetValue(model.getTargetValue())
                .measurementUnit(model.getMeasurementUnit())
                .lastCalculated(model.getLastCalculated())
                .calculationFormula(model.getCalculationFormula())
                .dataSource(model.getDataSource())
                .automated(model.isAutomated())
                .build();
    }

    public CampaignMetric modelToDomain(CampaignMetricModel model) {
        return CampaignMetric.builder()
                .id(CampaignMetricId.of(model.getId()))
                //.campaign(model.getCampaign())
                .name(model.getName())
                .type(model.getType())
                .description(model.getDescription())
                .value(model.getValue())
                .targetValue(model.getTargetValue())
                .measurementUnit(model.getMeasurementUnit())
                .lastCalculated(model.getLastCalculated())
                .calculationFormula(model.getCalculationFormula())
                .dataSource(model.getDataSource())
                .automated(model.isAutomated())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
    }

    private TargetAudience targetAudienceMap(TargetAudienceDetailsDTO dto) {
        return new TargetAudience(
                dto.description(),
                dto.demographics(),
                dto.geographicLocations(),
                dto.interests()
        );
    }

    private SuccessCriteria successCriteriaMap(SuccessCriteriaDetailsDTO dto) {
        return new SuccessCriteria(
                dto.description(),
                dto.metrics()
        );
    }
}
