package at.backend.CRM.MarketingProject.AutoMappers;

import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignAttribution;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-28T15:17:36-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CampaignAttributionMappersImpl implements CampaignAttributionMappers {

    @Override
    public CampaignAttribution inputToEntity(CampaignAttributionInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        CampaignAttribution campaignAttribution = new CampaignAttribution();

        campaignAttribution.setAttributionModel( input.getAttributionModel() );
        campaignAttribution.setAttributionPercentage( input.getAttributionPercentage() );
        campaignAttribution.setAttributedRevenue( input.getAttributedRevenue() );
        campaignAttribution.setTouchCount( input.getTouchCount() );

        return campaignAttribution;
    }

    @Override
    public CampaignAttributionDTO entityToDTO(CampaignAttribution entity) {
        if ( entity == null ) {
            return null;
        }

        CampaignAttributionDTO campaignAttributionDTO = new CampaignAttributionDTO();

        campaignAttributionDTO.setId( entity.getId() );
        campaignAttributionDTO.setAttributionModel( entity.getAttributionModel() );
        campaignAttributionDTO.setAttributionPercentage( entity.getAttributionPercentage() );
        campaignAttributionDTO.setAttributedRevenue( entity.getAttributedRevenue() );
        campaignAttributionDTO.setFirstTouchDate( entity.getFirstTouchDate() );
        campaignAttributionDTO.setLastTouchDate( entity.getLastTouchDate() );
        campaignAttributionDTO.setTouchCount( entity.getTouchCount() );

        return campaignAttributionDTO;
    }

    @Override
    public void updateEntity(CampaignAttribution entity, CampaignAttributionInsertDTO input) {
        if ( input == null ) {
            return;
        }

        entity.setAttributionModel( input.getAttributionModel() );
        entity.setAttributionPercentage( input.getAttributionPercentage() );
        entity.setAttributedRevenue( input.getAttributedRevenue() );
        entity.setTouchCount( input.getTouchCount() );
    }
}
