package at.backend.MarketingCompany.marketing.attribution.infrastructure.automappers;

import at.backend.MarketingCompany.crm.deal.domain.Deal;
import at.backend.MarketingCompany.marketing.attribution.api.repository.CampaignAttributionModel;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionDTO;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T16:58:16-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CampaignAttributionMappersImpl implements CampaignAttributionMappers {

    @Override
    public CampaignAttributionModel inputToEntity(CampaignAttributionInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        CampaignAttributionModel campaignAttributionModel = new CampaignAttributionModel();

        campaignAttributionModel.setAttributionModel( input.getAttributionModel() );
        campaignAttributionModel.setAttributionPercentage( input.getAttributionPercentage() );
        campaignAttributionModel.setAttributedRevenue( input.getAttributedRevenue() );
        campaignAttributionModel.setTouchCount( input.getTouchCount() );

        return campaignAttributionModel;
    }

    @Override
    public CampaignAttributionDTO entityToDTO(CampaignAttributionModel entity) {
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
    public void updateEntity(CampaignAttributionModel entity, CampaignAttributionInsertDTO input) {
        if ( input == null ) {
            return;
        }

        entity.setAttributionModel( input.getAttributionModel() );
        entity.setAttributionPercentage( input.getAttributionPercentage() );
        entity.setAttributedRevenue( input.getAttributedRevenue() );
        entity.setTouchCount( input.getTouchCount() );
    }

    private Long entityCampaignId(CampaignAttributionModel campaignAttributionModel) {
        if ( campaignAttributionModel == null ) {
            return null;
        }
        MarketingCampaignModel campaign = campaignAttributionModel.getCampaign();
        if ( campaign == null ) {
            return null;
        }
        Long id = campaign.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityDealId(CampaignAttributionModel campaignAttributionModel) {
        if ( campaignAttributionModel == null ) {
            return null;
        }
        Deal deal = campaignAttributionModel.getDeal();
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
