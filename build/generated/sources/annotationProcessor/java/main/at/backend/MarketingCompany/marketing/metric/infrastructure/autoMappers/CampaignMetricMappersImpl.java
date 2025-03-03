package at.backend.MarketingCompany.marketing.metric.infrastructure.autoMappers;

import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.marketing.metric.api.repository.CampaignMetric;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricDTO;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricInsertDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T16:58:16-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CampaignMetricMappersImpl implements CampaignMetricMappers {

    @Override
    public CampaignMetric inputToEntity(CampaignMetricInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        CampaignMetric campaignMetric = new CampaignMetric();

        campaignMetric.setName( input.getName() );
        campaignMetric.setDescription( input.getDescription() );
        campaignMetric.setType( input.getType() );
        campaignMetric.setValue( input.getValue() );
        campaignMetric.setTargetValue( input.getTargetValue() );
        campaignMetric.setMeasurementUnit( input.getMeasurementUnit() );
        campaignMetric.setCalculationFormula( input.getCalculationFormula() );
        campaignMetric.setDataSource( input.getDataSource() );
        campaignMetric.setAutomated( input.isAutomated() );

        return campaignMetric;
    }

    @Override
    public CampaignMetricDTO entityToDTO(CampaignMetric entity) {
        if ( entity == null ) {
            return null;
        }

        CampaignMetricDTO campaignMetricDTO = new CampaignMetricDTO();

        campaignMetricDTO.setCampaignId( entityCampaignId( entity ) );
        campaignMetricDTO.setId( entity.getId() );
        campaignMetricDTO.setName( entity.getName() );
        campaignMetricDTO.setDescription( entity.getDescription() );
        campaignMetricDTO.setType( entity.getType() );
        campaignMetricDTO.setValue( entity.getValue() );
        campaignMetricDTO.setTargetValue( entity.getTargetValue() );
        campaignMetricDTO.setMeasurementUnit( entity.getMeasurementUnit() );
        campaignMetricDTO.setLastCalculated( entity.getLastCalculated() );
        campaignMetricDTO.setCalculationFormula( entity.getCalculationFormula() );
        campaignMetricDTO.setDataSource( entity.getDataSource() );
        campaignMetricDTO.setAutomated( entity.isAutomated() );

        return campaignMetricDTO;
    }

    @Override
    public void updateEntity(CampaignMetric entity, CampaignMetricInsertDTO input) {
        if ( input == null ) {
            return;
        }

        entity.setName( input.getName() );
        entity.setDescription( input.getDescription() );
        entity.setType( input.getType() );
        entity.setValue( input.getValue() );
        entity.setTargetValue( input.getTargetValue() );
        entity.setMeasurementUnit( input.getMeasurementUnit() );
        entity.setCalculationFormula( input.getCalculationFormula() );
        entity.setDataSource( input.getDataSource() );
        entity.setAutomated( input.isAutomated() );
    }

    private Long entityCampaignId(CampaignMetric campaignMetric) {
        if ( campaignMetric == null ) {
            return null;
        }
        MarketingCampaignModel campaign = campaignMetric.getCampaign();
        if ( campaign == null ) {
            return null;
        }
        Long id = campaign.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
