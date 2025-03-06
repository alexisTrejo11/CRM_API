package at.backend.MarketingCompany.marketing.activity.infrastructure.AutoMappers;

import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.user.api.Model.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T20:42:47-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CampaignActivityMappersImpl implements CampaignActivityMappers {

    @Override
    public CampaignActivityModel inputToEntity(CampaignActivityInsertDTO input) {
        if ( input == null ) {
            return null;
        }

        CampaignActivityModel.CampaignActivityModelBuilder campaignActivityModel = CampaignActivityModel.builder();

        campaignActivityModel.name( input.getName() );
        campaignActivityModel.description( input.getDescription() );
        campaignActivityModel.activityType( input.getActivityType() );
        campaignActivityModel.plannedStartDate( input.getPlannedStartDate() );
        campaignActivityModel.plannedEndDate( input.getPlannedEndDate() );
        campaignActivityModel.plannedCost( input.getPlannedCost() );
        campaignActivityModel.successCriteria( input.getSuccessCriteria() );
        campaignActivityModel.targetAudience( input.getTargetAudience() );
        campaignActivityModel.deliveryChannel( input.getDeliveryChannel() );

        return campaignActivityModel.build();
    }

    @Override
    public CampaignActivityDTO entityToDTO(CampaignActivityModel entity) {
        if ( entity == null ) {
            return null;
        }

        CampaignActivityDTO.CampaignActivityDTOBuilder campaignActivityDTO = CampaignActivityDTO.builder();

        UUID id = entityAssignedToId( entity );
        if ( id != null ) {
            campaignActivityDTO.assignedTo( id.toString() );
        }
        campaignActivityDTO.id( entity.getId() );
        campaignActivityDTO.name( entity.getName() );
        campaignActivityDTO.description( entity.getDescription() );
        campaignActivityDTO.activityType( entity.getActivityType() );
        campaignActivityDTO.plannedStartDate( entity.getPlannedStartDate() );
        campaignActivityDTO.plannedEndDate( entity.getPlannedEndDate() );
        campaignActivityDTO.actualStartDate( entity.getActualStartDate() );
        campaignActivityDTO.actualEndDate( entity.getActualEndDate() );
        campaignActivityDTO.status( entity.getStatus() );
        campaignActivityDTO.plannedCost( entity.getPlannedCost() );
        campaignActivityDTO.actualCost( entity.getActualCost() );
        campaignActivityDTO.successCriteria( entity.getSuccessCriteria() );
        campaignActivityDTO.targetAudience( entity.getTargetAudience() );
        campaignActivityDTO.deliveryChannel( entity.getDeliveryChannel() );

        return campaignActivityDTO.build();
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

    private UUID entityAssignedToId(CampaignActivityModel campaignActivityModel) {
        if ( campaignActivityModel == null ) {
            return null;
        }
        User assignedTo = campaignActivityModel.getAssignedTo();
        if ( assignedTo == null ) {
            return null;
        }
        UUID id = assignedTo.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
