package at.backend.MarketingProject.AutoMappers;

import at.backend.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingProject.Models.CampaignInteraction;
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
public class CampaignInteractionMappersImpl implements CampaignInteractionMappers {

    @Override
    public CampaignInteraction inputToEntity(CampaignInteractionInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        CampaignInteraction campaignInteraction = new CampaignInteraction();

        campaignInteraction.setMarketingInteractionType( input.getMarketingInteractionType() );
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

        campaignInteractionDTO.setId( entity.getId() );
        campaignInteractionDTO.setMarketingInteractionType( entity.getMarketingInteractionType() );
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

        entity.setMarketingInteractionType( input.getMarketingInteractionType() );
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
}
