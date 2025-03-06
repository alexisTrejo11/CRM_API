package at.backend.MarketingCompany.marketing.activity.infrastructure.AutoMappers;

import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivityCost;
import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivitySchedule;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.domain.CampaignActivity;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.CampaignId;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ActivityMappers {

    public CampaignActivity inputToEntity(CampaignActivityInsertDTO insertDTO) {
        ActivitySchedule schedule = new ActivitySchedule(
                insertDTO.getPlannedStartDate(),
                insertDTO.getPlannedEndDate(),
                LocalDateTime.now(),
                null);
        ActivityCost cost = new ActivityCost(insertDTO.getPlannedCost(), insertDTO.getPlannedCost());

        return new CampaignActivity(
                CampaignId.of(insertDTO.getCampaignId()),
                insertDTO.getName(),
                insertDTO.getDescription(),
                insertDTO.getActivityType(),
                schedule,
                cost,
                insertDTO.getDeliveryChannel()
        );
    }

    public CampaignActivity modelToDomain(CampaignActivityModel model) {
        return new CampaignActivity(
                CampaignId.of(model.getCampaign().getId()),
                model.getName(),
                model.getDescription(),
                model.getActivityType(),
                new ActivitySchedule(model.getPlannedStartDate(), model.getPlannedEndDate(), model.getActualStartDate(), model.getActualEndDate()),
                new ActivityCost(model.getPlannedCost(), model.getActualCost()),
                model.getDeliveryChannel()
        );
    }

    public CampaignActivityModel domainToModel(CampaignActivity domain) {
        return CampaignActivityModel.builder()
                .id(UUID.fromString(domain.getId().toString()))
                .campaign(MarketingCampaignModel.builder().id(domain.getCampaignId().getValue()).build())
                .name(domain.getName())
                .description(domain.getDescription())
                .activityType(domain.getType())
                .plannedStartDate(domain.getSchedule().plannedStartDate())
                .plannedEndDate(domain.getSchedule().plannedEndDate())
                .actualStartDate(domain.getSchedule().actualStartDate())
                .actualEndDate(domain.getSchedule().actualEndDate())
                .status(domain.getStatus())
                .plannedCost(domain.getCost().plannedCost())
                .actualCost(domain.getCost().actualCost())
                .successCriteria(domain.getSuccessCriteria())
                .targetAudience(domain.getTargetAudience())
                .deliveryChannel(domain.getDeliveryChannel())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public void updateEntity(CampaignActivity existingActivity, CampaignActivityInsertDTO dto) {
        existingActivity.setCampaignId(CampaignId.of(dto.getCampaignId()));
        existingActivity.setName(dto.getName().trim());
        existingActivity.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : null);
        existingActivity.setType(dto.getActivityType());

        ActivitySchedule existingSchedule = existingActivity.getSchedule();
        LocalDateTime actualStart = existingSchedule != null ? existingSchedule.actualStartDate() : null;
        LocalDateTime actualEnd = existingSchedule != null ? existingSchedule.actualEndDate() : null;

        ActivitySchedule updatedSchedule = new ActivitySchedule(
                dto.getPlannedStartDate(),
                dto.getPlannedEndDate(),
                actualStart,
                actualEnd
        );
        existingActivity.setSchedule(updatedSchedule);

        ActivityCost existingCost = existingActivity.getCost();
        BigDecimal actualCost = existingCost != null ? existingCost.actualCost() : null;

        ActivityCost updatedCost = new ActivityCost(
                dto.getPlannedCost(),
                actualCost
        );
        existingActivity.setCost(updatedCost);

        existingActivity.setDeliveryChannel(dto.getDeliveryChannel().trim());
        existingActivity.setUpdatedAt(LocalDateTime.now());
    }



    public CampaignActivityDTO domainToDTO(CampaignActivity domain) {
        return CampaignActivityDTO.builder()
                .id(domain.getId().value())
                .name(domain.getName())
                .description(domain.getDescription())
                .activityType(domain.getType())
                .plannedStartDate(domain.getSchedule().plannedStartDate())
                .plannedEndDate(domain.getSchedule().plannedEndDate())
                .actualStartDate(domain.getSchedule().actualStartDate())
                .actualEndDate(domain.getSchedule().actualEndDate())
                .status(domain.getStatus())
                .plannedCost(domain.getCost().plannedCost())
                .actualCost(domain.getCost().actualCost())
                .assignedTo(domain.getAssignedTo() != null ? domain.getAssignedTo().value() : null)
                .campaignId(domain.getCampaignId().getValue())
                .successCriteria(domain.getSuccessCriteria())
                .targetAudience(domain.getTargetAudience())
                .deliveryChannel(domain.getDeliveryChannel())
                .build();
    }

    public CampaignActivityDTO modelToDTO(CampaignActivityModel model) {
        ActivitySchedule schedule = new ActivitySchedule(
                model.getPlannedStartDate(),
                model.getPlannedEndDate(),
                model.getActualStartDate(),
                model.getActualEndDate()
        );

        return CampaignActivityDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .activityType(model.getActivityType())
                .plannedStartDate(model.getPlannedStartDate())
                .plannedEndDate(model.getPlannedEndDate())
                .actualStartDate(model.getActualStartDate())
                .actualEndDate(model.getActualEndDate())
                .status(model.getStatus())
                .plannedCost(model.getPlannedCost())
                .actualCost(model.getActualCost())
                .assignedTo(model.getAssignedTo() != null ? model.getAssignedTo().getId() : null)
                .campaignId(model.getCampaign().getId())
                .successCriteria(model.getSuccessCriteria())
                .targetAudience(model.getTargetAudience())
                .deliveryChannel(model.getDeliveryChannel())
                .build();
    }
}

