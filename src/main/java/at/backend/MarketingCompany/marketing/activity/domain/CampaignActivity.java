package at.backend.MarketingCompany.marketing.activity.domain;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityType;
import at.backend.MarketingCompany.marketing.activity.domain.Exception.ActivityException;
import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivityCost;
import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivityId;
import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivitySchedule;
import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.CampaignId;
import at.backend.MarketingCompany.marketing.customer.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignActivity {
    private ActivityId id;
    private CampaignId campaignId;
    private String name;
    private String description;
    private ActivityType type;
    private ActivitySchedule schedule;
    private ActivityStatus status;
    private ActivityCost cost;
    private UserId assignedTo;
    private String successCriteria;
    private String targetAudience;
    private String deliveryChannel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CampaignActivity(
        CampaignId campaignId,
        String name,
        String description,
        ActivityType type,
        ActivitySchedule schedule,
        ActivityCost cost,
        String deliveryChannel
    ) {
        this.id = ActivityId.generate();
        this.campaignId = Objects.requireNonNull(campaignId, "Campaign ID is required");
        setName(name);
        setDescription(description);
        this.type = Objects.requireNonNull(type, "Activity type is required");
        this.schedule = Objects.requireNonNull(schedule, "Schedule is required");
        this.cost = Objects.requireNonNull(cost, "Cost details are required");
        setDeliveryChannel(deliveryChannel);
        this.status = ActivityStatus.PLANNED;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        
        validateInitialState();
    }

    private void validateInitialState() {
        if (schedule.plannedStartDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ActivityException("Activities must be planned at least 1 hour in advance");
        }
    }

    public void startActivity() {
        if (status != ActivityStatus.PLANNED) {
            throw new ActivityException("Only planned activities can be started");
        }
        if (LocalDateTime.now().isBefore(schedule.plannedStartDate())) {
            throw new ActivityException("Cannot start activity before planned start time");
        }
        this.status = ActivityStatus.IN_PROGRESS;
        this.schedule = schedule.withActualStart(LocalDateTime.now());
        this.updatedAt = LocalDateTime.now();
    }

    public void completeActivity() {
        if (status != ActivityStatus.IN_PROGRESS) {
            throw new ActivityException("Only in-progress activities can be completed");
        }
        this.status = ActivityStatus.COMPLETED;
        this.schedule = schedule.withActualEnd(LocalDateTime.now());
        this.updatedAt = LocalDateTime.now();
        validateCompletion();
    }

    public void updateActualCost(BigDecimal amount) {
        if (status == ActivityStatus.COMPLETED) {
            throw new ActivityException("Cannot update costs on completed activities");
        }
        this.cost = cost.withActualCost(amount);
        this.updatedAt = LocalDateTime.now();
        validateCostOverrun();
    }

    public void reschedule(LocalDateTime newStart, LocalDateTime newEnd) {
        if (status != ActivityStatus.PLANNED) {
            throw new ActivityException("Only planned activities can be rescheduled");
        }
        this.schedule = new ActivitySchedule(newStart, newEnd, null, null);
        this.updatedAt = LocalDateTime.now();
    }

    private void validateCompletion() {
        if (cost.actualCost() == null || cost.actualCost().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ActivityException("Actual cost must be recorded before completion");
        }
        if (schedule.actualEndDate() == null) {
            throw new ActivityException("Actual end date must be set for completion");
        }
    }

    private void validateCostOverrun() {
        if (cost.hasCostOverrun() && cost.actualCost().compareTo(cost.plannedCost().multiply(BigDecimal.valueOf(1.1))) > 0) {
            throw new ActivityException("Actual cost exceeds 10% over planned budget");
        }
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ActivityException("Activity name is required");
        }
        if (name.length() > 100) {
            throw new ActivityException("Name cannot exceed 100 characters");
        }
        this.name = name.trim();
    }

    private void setDescription(String description) {
        if (description != null && description.length() > 1000) {
            throw new ActivityException("Description cannot exceed 1000 characters");
        }
        this.description = description != null ? description.trim() : null;
    }

    private void setDeliveryChannel(String channel) {
        if (channel == null || channel.trim().isEmpty()) {
            throw new ActivityException("Delivery channel is required");
        }
        if (channel.length() > 50) {
            throw new ActivityException("Delivery channel cannot exceed 50 characters");
        }
        this.deliveryChannel = channel.trim();
    }
}