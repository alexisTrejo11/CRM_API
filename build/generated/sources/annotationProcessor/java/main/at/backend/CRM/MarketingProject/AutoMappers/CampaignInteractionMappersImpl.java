package at.backend.CRM.MarketingProject.AutoMappers;

import at.backend.CRM.CRM.Models.Customer;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignInteraction;
import at.backend.CRM.MarketingProject.Models.MarketingCampaign;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T13:08:46-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CampaignInteractionMappersImpl implements CampaignInteractionMappers {

    @Override
    public CampaignInteraction inputToEntity(CampaignInteractionInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        CampaignInteraction campaignInteraction = new CampaignInteraction();

        campaignInteraction.setInteractionType( input.getInteractionType() );
        campaignInteraction.setInteractionDate( input.getInteractionDate() );
        campaignInteraction.setSourceChannel( input.getSourceChannel() );
        campaignInteraction.setSourceMedium( input.getSourceMedium() );
        campaignInteraction.setSourceCampaign( input.getSourceCampaign() );
        campaignInteraction.setDeviceType( input.getDeviceType() );
        campaignInteraction.setIpAddress( input.getIpAddress() );
        campaignInteraction.setGeoLocation( input.getGeoLocation() );
        Map<String, String> map = input.getProperties();
        if ( map != null ) {
            campaignInteraction.setProperties( new LinkedHashMap<String, String>( map ) );
        }
        campaignInteraction.setDetails( input.getDetails() );
        campaignInteraction.setConversionValue( input.getConversionValue() );

        return campaignInteraction;
    }

    @Override
    public CampaignInteractionDTO entityToDTO(CampaignInteraction entity) {
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
    public void updateEntity(CampaignInteraction entity, CampaignInteractionInsertDTO input) {
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

    private Long entityCampaignId(CampaignInteraction campaignInteraction) {
        if ( campaignInteraction == null ) {
            return null;
        }
        MarketingCampaign campaign = campaignInteraction.getCampaign();
        if ( campaign == null ) {
            return null;
        }
        Long id = campaign.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityCustomerId(CampaignInteraction campaignInteraction) {
        if ( campaignInteraction == null ) {
            return null;
        }
        Customer customer = campaignInteraction.getCustomer();
        if ( customer == null ) {
            return null;
        }
        Long id = customer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
