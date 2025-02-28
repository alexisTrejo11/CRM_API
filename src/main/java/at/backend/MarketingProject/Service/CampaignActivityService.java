package at.backend.MarketingProject.Service;

import at.backend.MarketingProject.Models.CampaignActivity;
import at.backend.MarketingProject.Models.Utils.ActivityStatus;
import at.backend.MarketingProject.Repository.CampaignActivityRepository;
import at.backend.MarketingProject.Repository.MarketingCampaignRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import at.backend.MarketingProject.Models.MarketingCampaign;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class CampaignActivityService {

    @Autowired
    private CampaignActivityRepository campaignActivityRepository;

    @Autowired
    private MarketingCampaignRepository marketingCampaignRepository;

    public CampaignActivity createActivity(Long campaignId, CampaignActivity activity) {
        validateActivity(activity);
        MarketingCampaign campaign = marketingCampaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + campaignId));
        activity.setCampaign(campaign);
        return campaignActivityRepository.save(activity);
    }

    public CampaignActivity updateActivity(Long id, CampaignActivity updatedActivity) {
        Optional<CampaignActivity> existingActivity = campaignActivityRepository.findById(id);
        if (existingActivity.isPresent()) {
            CampaignActivity activity = existingActivity.get();
            activity.setName(updatedActivity.getName());
            activity.setDescription(updatedActivity.getDescription());
            activity.setActivityType(updatedActivity.getActivityType());
            activity.setPlannedStartDate(updatedActivity.getPlannedStartDate());
            activity.setPlannedEndDate(updatedActivity.getPlannedEndDate());
            activity.setActualStartDate(updatedActivity.getActualStartDate());
            activity.setActualEndDate(updatedActivity.getActualEndDate());
            activity.setStatus(updatedActivity.getStatus());
            activity.setPlannedCost(updatedActivity.getPlannedCost());
            activity.setActualCost(updatedActivity.getActualCost());
            activity.setAssignedTo(updatedActivity.getAssignedTo());
            activity.setSuccessCriteria(updatedActivity.getSuccessCriteria());
            activity.setTargetAudience(updatedActivity.getTargetAudience());
            activity.setDeliveryChannel(updatedActivity.getDeliveryChannel());
            return campaignActivityRepository.save(activity);
        } else {
            throw new RuntimeException("Activity not found with ID: " + id);
        }
    }

    public void deleteActivity(Long id) {
        Optional<CampaignActivity> activity = campaignActivityRepository.findById(id);
        if (activity.isPresent()) {
            campaignActivityRepository.delete(activity.get());
        } else {
            throw new RuntimeException("Activity not found with ID: " + id);
        }
    }

    public CampaignActivity getActivityById(Long id) {
        return campaignActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));
    }

    public List<CampaignActivity> getActivitiesByCampaignId(Long campaignId) {
        return campaignActivityRepository.findByCampaignId(campaignId);
    }

    public List<CampaignActivity> getActivitiesByStatus(ActivityStatus status) {
        return campaignActivityRepository.findByStatus(status);
    }

    public CampaignActivity startActivity(Long id) {
        CampaignActivity activity = getActivityById(id);
        if (activity.getStatus() == ActivityStatus.PLANNED) {
            activity.setStatus(ActivityStatus.IN_PROGRESS);
            activity.setActualStartDate(LocalDateTime.now());
            return campaignActivityRepository.save(activity);
        } else {
            throw new RuntimeException("Activity cannot be started from current status: " + activity.getStatus());
        }
    }

    public CampaignActivity completeActivity(Long id) {
        CampaignActivity activity = getActivityById(id);
        if (activity.getStatus() == ActivityStatus.IN_PROGRESS) {
            activity.setStatus(ActivityStatus.COMPLETED);
            activity.setActualEndDate(LocalDateTime.now());
            return campaignActivityRepository.save(activity);
        } else {
            throw new RuntimeException("Activity cannot be completed from current status: " + activity.getStatus());
        }
    }

    public BigDecimal calculateRemainingBudget(Long id) {
        CampaignActivity activity = getActivityById(id);
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
}