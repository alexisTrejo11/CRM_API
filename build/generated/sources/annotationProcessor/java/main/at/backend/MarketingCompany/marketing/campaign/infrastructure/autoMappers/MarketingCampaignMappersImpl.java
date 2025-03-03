package at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers;

import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T16:58:16-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class MarketingCampaignMappersImpl implements MarketingCampaignMappers {

    @Override
    public MarketingCampaignModel inputToEntity(MarketingCampaignInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        MarketingCampaignModel marketingCampaignModel = new MarketingCampaignModel();

        marketingCampaignModel.setName( input.getName() );
        marketingCampaignModel.setDescription( input.getDescription() );
        marketingCampaignModel.setStartDate( input.getStartDate() );
        marketingCampaignModel.setEndDate( input.getEndDate() );
        marketingCampaignModel.setBudget( input.getBudget() );
        marketingCampaignModel.setType( input.getType() );
        marketingCampaignModel.setTargetAudience( input.getTargetAudience() );
        marketingCampaignModel.setSuccessCriteria( input.getSuccessCriteria() );
        Map<String, Double> map = input.getTargets();
        if ( map != null ) {
            marketingCampaignModel.setTargets( new LinkedHashMap<String, Double>( map ) );
        }

        return marketingCampaignModel;
    }

    @Override
    public MarketingCampaignDTO entityToDTO(MarketingCampaignModel entity) {
        if ( entity == null ) {
            return null;
        }

        MarketingCampaignDTO marketingCampaignDTO = new MarketingCampaignDTO();

        marketingCampaignDTO.setId( entity.getId() );
        marketingCampaignDTO.setName( entity.getName() );
        marketingCampaignDTO.setDescription( entity.getDescription() );
        marketingCampaignDTO.setStartDate( entity.getStartDate() );
        marketingCampaignDTO.setEndDate( entity.getEndDate() );
        marketingCampaignDTO.setBudget( entity.getBudget() );
        marketingCampaignDTO.setCostToDate( entity.getCostToDate() );
        marketingCampaignDTO.setStatus( entity.getStatus() );
        marketingCampaignDTO.setType( entity.getType() );
        marketingCampaignDTO.setTargetAudience( entity.getTargetAudience() );
        marketingCampaignDTO.setSuccessCriteria( entity.getSuccessCriteria() );
        Map<String, Double> map = entity.getTargets();
        if ( map != null ) {
            marketingCampaignDTO.setTargets( new LinkedHashMap<String, Double>( map ) );
        }

        return marketingCampaignDTO;
    }

    @Override
    public void updateEntity(MarketingCampaignModel entity, MarketingCampaignInsertDTO input) {
        if ( input == null ) {
            return;
        }

        entity.setName( input.getName() );
        entity.setDescription( input.getDescription() );
        entity.setStartDate( input.getStartDate() );
        entity.setEndDate( input.getEndDate() );
        entity.setBudget( input.getBudget() );
        entity.setType( input.getType() );
        entity.setTargetAudience( input.getTargetAudience() );
        entity.setSuccessCriteria( input.getSuccessCriteria() );
        if ( entity.getTargets() != null ) {
            Map<String, Double> map = input.getTargets();
            if ( map != null ) {
                entity.getTargets().clear();
                entity.getTargets().putAll( map );
            }
            else {
                entity.setTargets( null );
            }
        }
        else {
            Map<String, Double> map = input.getTargets();
            if ( map != null ) {
                entity.setTargets( new LinkedHashMap<String, Double>( map ) );
            }
        }
    }
}
