package at.backend.MarketingCompany.marketing.interaction.infrastructure.autoMappers;

import at.backend.MarketingCompany.customer.domain.Customer;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.marketing.interaction.api.repository.CampaignInteractionModel;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionDTO;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionInsertDTO;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T15:19:10-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CampaignInteractionMappersImpl implements CampaignInteractionMappers {

    @Override
    public CampaignInteractionModel inputToEntity(CampaignInteractionInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        CampaignInteractionModel campaignInteractionModel = new CampaignInteractionModel();

        campaignInteractionModel.setInteractionType( input.getInteractionType() );
        campaignInteractionModel.setInteractionDate( input.getInteractionDate() );
        campaignInteractionModel.setSourceChannel( input.getSourceChannel() );
        campaignInteractionModel.setSourceMedium( input.getSourceMedium() );
        campaignInteractionModel.setSourceCampaign( input.getSourceCampaign() );
        campaignInteractionModel.setDeviceType( input.getDeviceType() );
        campaignInteractionModel.setIpAddress( input.getIpAddress() );
        campaignInteractionModel.setGeoLocation( input.getGeoLocation() );
        Map<String, String> map = input.getProperties();
        if ( map != null ) {
            campaignInteractionModel.setProperties( new LinkedHashMap<String, String>( map ) );
        }
        campaignInteractionModel.setDetails( input.getDetails() );
        campaignInteractionModel.setConversionValue( input.getConversionValue() );

        return campaignInteractionModel;
    }

    @Override
    public CampaignInteractionDTO entityToDTO(CampaignInteractionModel entity) {
        if ( entity == null ) {
            return null;
        }

        CampaignInteractionDTO campaignInteractionDTO = new CampaignInteractionDTO();

        campaignInteractionDTO.setCampaignId( entityCampaignId( entity ) );
        campaignInteractionDTO.setCustomerId( entityCustomerId( entity ) );
        campaignInteractionDTO.setId( entity.getId() );
        campaignInteractionDTO.setInteractionType( entity.getInteractionType() );
        campaignInteractionDTO.setInteractionDate( entity.getInteractionDate() );
        campaignInteractionDTO.setSourceChannel( entity.getSourceChannel() );
        campaignInteractionDTO.setSourceMedium( entity.getSourceMedium() );
        campaignInteractionDTO.setSourceCampaign( entity.getSourceCampaign() );
        campaignInteractionDTO.setDeviceType( entity.getDeviceType() );
        campaignInteractionDTO.setIpAddress( entity.getIpAddress() );
        campaignInteractionDTO.setGeoLocation( entity.getGeoLocation() );
        Map<String, String> map = entity.getProperties();
        if ( map != null ) {
            campaignInteractionDTO.setProperties( new LinkedHashMap<String, String>( map ) );
        }
        campaignInteractionDTO.setDetails( entity.getDetails() );
        campaignInteractionDTO.setConversionValue( entity.getConversionValue() );

        return campaignInteractionDTO;
    }

    @Override
    public void updateEntity(CampaignInteractionModel entity, CampaignInteractionInsertDTO input) {
        if ( input == null ) {
            return;
        }

        entity.setInteractionType( input.getInteractionType() );
        entity.setInteractionDate( input.getInteractionDate() );
        entity.setSourceChannel( input.getSourceChannel() );
        entity.setSourceMedium( input.getSourceMedium() );
        entity.setSourceCampaign( input.getSourceCampaign() );
        entity.setDeviceType( input.getDeviceType() );
        entity.setIpAddress( input.getIpAddress() );
        entity.setGeoLocation( input.getGeoLocation() );
        if ( entity.getProperties() != null ) {
            Map<String, String> map = input.getProperties();
            if ( map != null ) {
                entity.getProperties().clear();
                entity.getProperties().putAll( map );
            }
            else {
                entity.setProperties( null );
            }
        }
        else {
            Map<String, String> map = input.getProperties();
            if ( map != null ) {
                entity.setProperties( new LinkedHashMap<String, String>( map ) );
            }
        }
        entity.setDetails( input.getDetails() );
        entity.setConversionValue( input.getConversionValue() );
    }

    private UUID entityCampaignId(CampaignInteractionModel campaignInteractionModel) {
        if ( campaignInteractionModel == null ) {
            return null;
        }
        MarketingCampaignModel campaign = campaignInteractionModel.getCampaign();
        if ( campaign == null ) {
            return null;
        }
        UUID id = campaign.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID entityCustomerId(CampaignInteractionModel campaignInteractionModel) {
        if ( campaignInteractionModel == null ) {
            return null;
        }
        Customer customer = campaignInteractionModel.getCustomer();
        if ( customer == null ) {
            return null;
        }
        UUID id = customer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
