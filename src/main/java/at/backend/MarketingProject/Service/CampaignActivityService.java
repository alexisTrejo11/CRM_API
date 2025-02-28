package at.backend.MarketingProject.Service;

import at.backend.MarketingProject.AutoMappers.CampaignActivityMappers;
import at.backend.MarketingProject.DTOs.CampaignActivityDTO;
import at.backend.MarketingProject.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingProject.Models.CampaignActivity;
import at.backend.MarketingProject.Models.Utils.ActivityStatus;
import at.backend.MarketingProject.Repository.CampaignActivityRepository;
import at.backend.MarketingProject.Repository.MarketingCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import at.backend.MarketingProject.Models.MarketingCampaign;

@Service
@RequiredArgsConstructor
public class CampaignActivityService {

    private final CampaignActivityRepository campaignActivityRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final CampaignActivityMappers campaignActivityMappers;

    public CampaignActivityDTO createActivity(CampaignActivityInsertDTO input) {
        CampaignActivity activity = campaignActivityMappers.inputToEntity(input);

        validateActivity(activity);

        MarketingCampaign campaign = getCampaign(input.getCampaignId());
        activity.setCampaign(campaign);

        campaignActivityRepository.save(activity);

        return campaignActivityMappers.entityToDTO(activity);
    }

    public CampaignActivityDTO updateActivity(Long id, CampaignActivityInsertDTO input) {
        CampaignActivity existingActivity = campaignActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));

        campaignActivityMappers.updateEntity(existingActivity, input);

        campaignActivityRepository.save(existingActivity);

        return campaignActivityMappers.entityToDTO(existingActivity);
    }

    public void deleteActivity(Long id) {
        CampaignActivity activity = campaignActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));

        campaignActivityRepository.delete(activity);
    }

    public CampaignActivityDTO getActivityById(Long id) {
        return campaignActivityRepository.findById(id)
                .map(campaignActivityMappers::entityToDTO)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));
    }

    public List<CampaignActivity> getActivitiesByCampaignId(Long campaignId) {
        return campaignActivityRepository.findByCampaignId(campaignId);
    }

    public List<CampaignActivity> getActivitiesByStatus(ActivityStatus status) {
        return campaignActivityRepository.findByStatus(status);
    }

    public CampaignActivity startActivity(Long id) {
        CampaignActivity activity = getActivity(id);
        if (activity.getStatus() == ActivityStatus.PLANNED) {
            activity.setStatus(ActivityStatus.IN_PROGRESS);
            activity.setActualStartDate(LocalDateTime.now());
            return campaignActivityRepository.save(activity);
        } else {
            throw new RuntimeException("Activity cannot be started from current status: " + activity.getStatus());
        }
    }

    public CampaignActivity completeActivity(Long id) {
        CampaignActivity activity = getActivity(id);
        if (activity.getStatus() == ActivityStatus.IN_PROGRESS) {
            activity.setStatus(ActivityStatus.COMPLETED);
            activity.setActualEndDate(LocalDateTime.now());
            return campaignActivityRepository.save(activity);
        } else {
            throw new RuntimeException("Activity cannot be completed from current status: " + activity.getStatus());
        }
    }

    public BigDecimal calculateRemainingBudget(Long id) {
        CampaignActivity activity = getActivity(id);
        return activity.getPlannedCost().subtract(activity.getActualCost() != null ? activity.getActualCost() : BigDecimal.ZERO);
    }

    private void validateActivity(CampaignActivity activity) {
        if (activity.getName() == null || activity.getName().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty");
        }
        if (activity.getPlannedStartDate() == null || activity.getPlannedStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Planned start date must be in the future");
        }
        if (activity.getPlannedCost() == null || activity.getPlannedCost().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Planned cost must be greater than zero");
        }
    }

    private CampaignActivity getActivity(Long activityID) {
        return campaignActivityRepository.findById(activityID)
                .orElseThrow(() -> new RuntimeException("Campaign Activity not found with ID: " + activityID));
    }

    private MarketingCampaign getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + campaignID));
    }
}