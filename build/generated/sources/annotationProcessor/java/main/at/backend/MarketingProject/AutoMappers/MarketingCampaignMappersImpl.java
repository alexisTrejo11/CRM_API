package at.backend.MarketingProject.AutoMappers;

import at.backend.MarketingProject.DTOs.MarketingCampaignDTO;
import at.backend.MarketingProject.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingProject.Models.MarketingCampaign;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-28T14:49:06-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class MarketingCampaignMappersImpl implements MarketingCampaignMappers {

    @Override
    public MarketingCampaign inputToEntity(MarketingCampaignInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        MarketingCampaign marketingCampaign = new MarketingCampaign();

        marketingCampaign.setName( input.getName() );
        marketingCampaign.setDescription( input.getDescription() );
        marketingCampaign.setStartDate( input.getStartDate() );
        marketingCampaign.setEndDate( input.getEndDate() );
        marketingCampaign.setBudget( input.getBudget() );
        marketingCampaign.setType( input.getType() );
        marketingCampaign.setTargetAudience( input.getTargetAudience() );
        marketingCampaign.setSuccessCriteria( input.getSuccessCriteria() );
        Map<String, Double> map = input.getTargets();
        if ( map != null ) {
            marketingCampaign.setTargets( new LinkedHashMap<String, Double>( map ) );
        }

        return marketingCampaign;
    }

    @Override
    public MarketingCampaignDTO entityToDTO(MarketingCampaign entity) {
        if ( entity == null ) {
            return null;
        }

        MarketingCampaignDTO marketingCampaignDTO = new MarketingCampaignDTO();

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
    public void updateEntity(MarketingCampaign entity, MarketingCampaignInsertDTO input) {
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
