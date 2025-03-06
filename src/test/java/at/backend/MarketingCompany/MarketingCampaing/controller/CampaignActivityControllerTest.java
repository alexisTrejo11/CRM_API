package at.backend.MarketingCompany.MarketingCampaing.controller;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityType;
import at.backend.MarketingCompany.marketing.activity.api.controller.CampaignActivityController;
import at.backend.MarketingCompany.marketing.activity.api.service.CampaignActivityService;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CampaignActivityControllerTest {

    @Mock
    private CampaignActivityService campaignActivityService;

    @InjectMocks
    private CampaignActivityController controller;

    private UUID activityId;
    private UUID campaignId;
    private CampaignActivityDTO activityDTO;
    private CampaignActivityInsertDTO insertDTO;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        activityId = UUID.randomUUID();
        campaignId = UUID.randomUUID();
        now = LocalDateTime.now();

        // Set up test data
        activityDTO = CampaignActivityDTO.builder()
                .id(activityId)
                .name("Test Activity")
                .description("Test Description")
                .activityType(ActivityType.EMAIL_BLAST)
                .plannedStartDate(now.plusDays(1))
                .plannedEndDate(now.plusDays(5))
                .status(ActivityStatus.PLANNED)
                .plannedCost(BigDecimal.valueOf(1000))
                .campaignId(campaignId)
                .successCriteria("10% engagement")
                .targetAudience("Young professionals")
                .deliveryChannel("Email")
                .build();

        insertDTO = CampaignActivityInsertDTO.builder()
                .name("Test Activity")
                .description("Test Description")
                .activityType(ActivityType.EMAIL_BLAST)
                .plannedStartDate(now.plusDays(1))
                .plannedEndDate(now.plusDays(5))
                .plannedCost(BigDecimal.valueOf(1000))
                .campaignId(campaignId)
                .successCriteria("10% engagement")
                .targetAudience("Young professionals")
                .deliveryChannel("Email")
                .build();
    }

    @Test
    void getActivityById_ShouldReturnActivity() {
        // Arrange
        when(campaignActivityService.getById(activityId)).thenReturn(activityDTO);

        // Act
        CampaignActivityDTO result = controller.getActivityById(activityId);

        // Assert
        assertNotNull(result);
        assertEquals(activityId, result.getId());
        assertEquals("Test Activity", result.getName());
        verify(campaignActivityService).getById(activityId);
    }

    @Test
    void getActivitiesByCampaignId_ShouldReturnActivities() {
        // Arrange
        List<CampaignActivityDTO> activities = Arrays.asList(activityDTO);
        when(campaignActivityService.getActivitiesByCampaignId(campaignId)).thenReturn(activities);

        // Act
        List<CampaignActivityDTO> result = controller.getActivitiesByCampaignId(campaignId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(activityId, result.get(0).getId());
        verify(campaignActivityService).getActivitiesByCampaignId(campaignId);
    }

    @Test
    void getActivitiesByStatus_ShouldReturnActivities() {
        // Arrange
        ActivityStatus status = ActivityStatus.PLANNED;
        List<CampaignActivityDTO> activities = Arrays.asList(activityDTO);
        when(campaignActivityService.getActivitiesByStatus(status)).thenReturn(activities);

        // Act
        List<CampaignActivityDTO> result = controller.getActivitiesByStatus(status);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ActivityStatus.PLANNED, result.get(0).getStatus());
        verify(campaignActivityService).getActivitiesByStatus(status);
    }

    @Test
    void createActivity_ShouldCreateAndReturnActivity() {
        // Arrange
        when(campaignActivityService.create(any(CampaignActivityInsertDTO.class))).thenReturn(activityDTO);

        // Act
        CampaignActivityDTO result = controller.createActivity(insertDTO);

        // Assert
        assertNotNull(result);
        assertEquals(activityId, result.getId());
        assertEquals("Test Activity", result.getName());
        verify(campaignActivityService).create(insertDTO);
    }

    @Test
    void updateActivity_ShouldUpdateAndReturnActivity() {
        // Arrange
        when(campaignActivityService.update(eq(activityId), any(CampaignActivityInsertDTO.class)))
                .thenReturn(activityDTO);

        // Act
        CampaignActivityDTO result = controller.updateActivity(insertDTO, activityId);

        // Assert
        assertNotNull(result);
        assertEquals(activityId, result.getId());
        assertEquals("Test Activity", result.getName());
        verify(campaignActivityService).update(activityId, insertDTO);
    }

    @Test
    void deleteActivity_ShouldDeleteAndReturnTrue() {
        // Arrange
        doNothing().when(campaignActivityService).delete(activityId);

        // Act
        boolean result = controller.deleteActivity(activityId);

        // Assert
        assertTrue(result);
        verify(campaignActivityService).delete(activityId);
    }

    @Test
    void startActivity_ShouldStartAndReturnActivity() {
        // Arrange
        CampaignActivityDTO startedDTO = CampaignActivityDTO.builder()
                .id(activityId)
                .name("Test Activity")
                .status(ActivityStatus.IN_PROGRESS)
                .actualStartDate(now)
                .build();

        when(campaignActivityService.startActivity(activityId)).thenReturn(startedDTO);

        // Act
        CampaignActivityDTO result = controller.startActivity(activityId);

        // Assert
        assertNotNull(result);
        assertEquals(ActivityStatus.IN_PROGRESS, result.getStatus());
        verify(campaignActivityService).startActivity(activityId);
    }

    @Test
    void completeActivity_ShouldCompleteAndReturnActivity() {
        // Arrange
        CampaignActivityDTO completedDTO = CampaignActivityDTO.builder()
                .id(activityId)
                .name("Test Activity")
                .status(ActivityStatus.COMPLETED)
                .actualEndDate(now)
                .build();

        when(campaignActivityService.completeActivity(activityId)).thenReturn(completedDTO);

        // Act
        CampaignActivityDTO result = controller.completeActivity(activityId);

        // Assert
        assertNotNull(result);
        assertEquals(ActivityStatus.COMPLETED, result.getStatus());
        verify(campaignActivityService).completeActivity(activityId);
    }
}