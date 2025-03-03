package at.backend.MarketingCompany.marketing.activity.infrastructure.AutoMappers;

import at.backend.MarketingCompany.customer.User;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T16:58:16-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CampaignActivityMappersImpl implements CampaignActivityMappers {

    @Override
    public CampaignActivityModel inputToEntity(CampaignActivityInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        CampaignActivityModel campaignActivityModel = new CampaignActivityModel();

        campaignActivityModel.setName( input.getName() );
        campaignActivityModel.setDescription( input.getDescription() );
        campaignActivityModel.setActivityType( input.getActivityType() );
        campaignActivityModel.setPlannedStartDate( input.getPlannedStartDate() );
        campaignActivityModel.setPlannedEndDate( input.getPlannedEndDate() );
        campaignActivityModel.setPlannedCost( input.getPlannedCost() );
        campaignActivityModel.setSuccessCriteria( input.getSuccessCriteria() );
        campaignActivityModel.setTargetAudience( input.getTargetAudience() );
        campaignActivityModel.setDeliveryChannel( input.getDeliveryChannel() );

        return campaignActivityModel;
    }

    @Override
    public CampaignActivityDTO entityToDTO(CampaignActivityModel entity) {
        if ( entity == null ) {
            return null;
        }

        CampaignActivityDTO campaignActivityDTO = new CampaignActivityDTO();

        Long id = entityAssignedToId( entity );
        if ( id != null ) {
            campaignActivityDTO.setAssignedTo( String.valueOf( id ) );
        }
        campaignActivityDTO.setId( entity.getId() );
        campaignActivityDTO.setName( entity.getName() );
        campaignActivityDTO.setDescription( entity.getDescription() );
        campaignActivityDTO.setActivityType( entity.getActivityType() );
        campaignActivityDTO.setPlannedStartDate( entity.getPlannedStartDate() );
        campaignActivityDTO.setPlannedEndDate( entity.getPlannedEndDate() );
        campaignActivityDTO.setActualStartDate( entity.getActualStartDate() );
        campaignActivityDTO.setActualEndDate( entity.getActualEndDate() );
        campaignActivityDTO.setStatus( entity.getStatus() );
        campaignActivityDTO.setPlannedCost( entity.getPlannedCost() );
        campaignActivityDTO.setActualCost( entity.getActualCost() );
        campaignActivityDTO.setSuccessCriteria( entity.getSuccessCriteria() );
        campaignActivityDTO.setTargetAudience( entity.getTargetAudience() );
        campaignActivityDTO.setDeliveryChannel( entity.getDeliveryChannel() );

        return campaignActivityDTO;
    }

    @Override
    public void updateEntity(CampaignActivityModel existingActivity, CampaignActivityInsertDTO input) {
        if ( input == null ) {
            return;
        }

        existingActivity.setName( input.getName() );
        existingActivity.setDescription( input.getDescription() );
        existingActivity.setActivityType( input.getActivityType() );
        existingActivity.setPlannedStartDate( input.getPlannedStartDate() );
        existingActivity.setPlannedEndDate( input.getPlannedEndDate() );
        existingActivity.setPlannedCost( input.getPlannedCost() );
        existingActivity.setSuccessCriteria( input.getSuccessCriteria() );
        existingActivity.setTargetAudience( input.getTargetAudience() );
        existingActivity.setDeliveryChannel( input.getDeliveryChannel() );
    }

    private Long entityAssignedToId(CampaignActivityModel campaignActivityModel) {
        if ( campaignActivityModel == null ) {
            return null;
        }
        User assignedTo = campaignActivityModel.getAssignedTo();
        if ( assignedTo == null ) {
            return null;
        }
        Long id = assignedTo.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
