package at.backend.MarketingCompany.MarketingCampaing.domain;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityType;
import at.backend.MarketingCompany.marketing.activity.domain.CampaignActivity;
import at.backend.MarketingCompany.marketing.activity.domain.Exception.ActivityException;
import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivityCost;
import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivitySchedule;
import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.CampaignId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CampaignActivityTest {

    private CampaignActivity activity;
    private CampaignId campaignId;
    private ActivitySchedule schedule;
    private ActivityCost cost;

    @BeforeEach
    void setUp() {
        campaignId = CampaignId.generate();
        schedule = new ActivitySchedule(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusDays(1), null, null);
        cost = new ActivityCost(new BigDecimal("1000.00"), null);

        activity = new CampaignActivity(
                campaignId,
                "New Campaign",
                "Description of the campaign",
                ActivityType.WEBINAR,
                schedule,
                cost,
                "Email"
        );
    }

    // Update Schedule, to allow to start activity (to create activity needs schedule in future but for start it needs schedule in past)
    private void updateScheduleForActivity(LocalDateTime start, LocalDateTime end) {
        schedule = new ActivitySchedule(start, end, null, null);
        activity.setSchedule(schedule);
    }

    @Test
    void shouldCreateCampaignActivityWithPlannedStatus() {
        assertNotNull(activity);
        assertEquals(ActivityStatus.PLANNED, activity.getStatus());
    }

    @Test
    void shouldThrowExceptionIfStartTimeIsTooSoon() {
        ActivitySchedule invalidSchedule = new ActivitySchedule(LocalDateTime.now(), LocalDateTime.now().plusDays(1), null, null);

        Exception exception = assertThrows(ActivityException.class, () -> {
            new CampaignActivity(campaignId, "Invalid", "Description", ActivityType.WEBINAR, invalidSchedule, cost, "Email");
        });

        assertEquals("Activities must be planned at least 1 hour in advance", exception.getMessage());
    }

    @Test
    void shouldStartActivityIfConditionsAreMet() {
        updateScheduleForActivity(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusDays(1));

        activity.startActivity();
        assertEquals(ActivityStatus.IN_PROGRESS, activity.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenStartingBeforePlannedTime() {
        ActivitySchedule earlySchedule = new ActivitySchedule(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusDays(1), null, null);
        CampaignActivity earlyActivity = new CampaignActivity(campaignId, "Test", "Desc", ActivityType.WEBINAR, earlySchedule, cost, "Email");

        Exception exception = assertThrows(ActivityException.class, earlyActivity::startActivity);
        assertEquals("Cannot start activity before planned start time", exception.getMessage());
    }

    @Test
    void shouldCompleteActivityIfConditionsAreMet() {
        updateScheduleForActivity(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusDays(1));

        activity.startActivity();
        activity.updateActualCost(new BigDecimal("1100.00"));
        activity.completeActivity();

        assertEquals(ActivityStatus.COMPLETED, activity.getStatus());
    }

    @Test
    void shouldThrowExceptionIfCompletingWithoutCost() {
        updateScheduleForActivity(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusDays(1));

        activity.startActivity();

        Exception exception = assertThrows(ActivityException.class, activity::completeActivity);
        assertEquals("Actual cost must be recorded before completion", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfUpdatingCostAfterCompletion() {
        updateScheduleForActivity(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusDays(1));

        activity.startActivity();
        activity.updateActualCost(new BigDecimal("1100.00"));
        activity.completeActivity();

        Exception exception = assertThrows(ActivityException.class, () -> activity.updateActualCost(new BigDecimal("1200.00")));
        assertEquals("Cannot update costs on completed activities", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfCostOverrunExceedsTenPercent() {
        updateScheduleForActivity(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusDays(1));

        activity.startActivity();

        Exception exception = assertThrows(ActivityException.class, () -> activity.updateActualCost(new BigDecimal("1200.01")));
        assertEquals("Actual cost exceeds 10% over planned budget", exception.getMessage());
    }

    @Test
    void shouldRescheduleIfActivityIsPlanned() {
        LocalDateTime newStart = LocalDateTime.now().plusDays(2);
        LocalDateTime newEnd = LocalDateTime.now().plusDays(3);

        activity.reschedule(newStart, newEnd);

        assertEquals(newStart, activity.getSchedule().plannedStartDate());
        assertEquals(newEnd, activity.getSchedule().plannedEndDate());
    }

    @Test
    void shouldThrowExceptionIfReschedulingAnInProgressActivity() {
        updateScheduleForActivity(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusDays(1));

        activity.startActivity();

        Exception exception = assertThrows(ActivityException.class, () -> activity.reschedule(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3)));
        assertEquals("Only planned activities can be rescheduled", exception.getMessage());
    }
}
