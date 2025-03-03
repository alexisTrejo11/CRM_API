package at.backend.CRM.MarketingProject.AutoMappers;

import at.backend.CRM.CRM.Models.Deal;
import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignAttribution;
import at.backend.CRM.MarketingProject.Models.MarketingCampaign;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T12:58:49-0600",
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

        campaignAttributionDTO.setCampaignId( entityCampaignId( entity ) );
        campaignAttributionDTO.setDealId( entityDealId( entity ) );
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

    private Long entityCampaignId(CampaignAttribution campaignAttribution) {
        if ( campaignAttribution == null ) {
            return null;
        }
        MarketingCampaign campaign = campaignAttribution.getCampaign();
        if ( campaign == null ) {
            return null;
        }
        Long id = campaign.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityDealId(CampaignAttribution campaignAttribution) {
        if ( campaignAttribution == null ) {
            return null;
        }
        Deal deal = campaignAttribution.getDeal();
        if ( deal == null ) {
            return null;
        }
        Long id = deal.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
