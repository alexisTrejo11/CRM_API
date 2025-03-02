package at.backend.CRM.MarketingProject.AutoMappers;

import at.backend.CRM.CRM.Models.User;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignActivity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-28T16:25:06-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CampaignActivityMappersImpl implements CampaignActivityMappers {

    @Override
    public CampaignActivity inputToEntity(CampaignActivityInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        CampaignActivity campaignActivity = new CampaignActivity();

        campaignActivity.setName( input.getName() );
        campaignActivity.setDescription( input.getDescription() );
        campaignActivity.setActivityType( input.getActivityType() );
        campaignActivity.setPlannedStartDate( input.getPlannedStartDate() );
        campaignActivity.setPlannedEndDate( input.getPlannedEndDate() );
        campaignActivity.setPlannedCost( input.getPlannedCost() );
        campaignActivity.setSuccessCriteria( input.getSuccessCriteria() );
        campaignActivity.setTargetAudience( input.getTargetAudience() );
        campaignActivity.setDeliveryChannel( input.getDeliveryChannel() );

        return campaignActivity;
    }

    @Override
    public CampaignActivityDTO entityToDTO(CampaignActivity entity) {
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
    public void updateEntity(CampaignActivity existingActivity, CampaignActivityInsertDTO input) {
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

    private Long entityAssignedToId(CampaignActivity campaignActivity) {
        if ( campaignActivity == null ) {
            return null;
        }
        User assignedTo = campaignActivity.getAssignedTo();
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
