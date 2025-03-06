package at.backend.MarketingCompany.MarketingCampaing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import at.backend.MarketingCompany.common.exceptions.InvalidInputException;
import at.backend.MarketingCompany.common.exceptions.InvalidStatusTransitionException;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityType;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityRepository;
import at.backend.MarketingCompany.marketing.activity.api.service.CampaignActivityServiceImpl;
import at.backend.MarketingCompany.marketing.activity.infrastructure.AutoMappers.CampaignActivityMappers;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CampaignActivityServiceImplTest {

    @Mock
    private CampaignActivityRepository campaignActivityRepository;

    @Mock
    private MarketingCampaignRepository marketingCampaignRepository;

    @Mock
    private CampaignActivityMappers campaignActivityMappers;

    @InjectMocks
    private CampaignActivityServiceImpl service;

    private final UUID CAMPAIGN_ID = UUID.randomUUID();
    private final UUID ACTIVITY_ID = UUID.randomUUID();
    private final LocalDateTime NOW = LocalDateTime.now();
    private static final LocalDateTime FIXED_TIME = LocalDateTime.of(2026, 10, 1, 12, 0);
    private Clock fixedClock = Clock.fixed(FIXED_TIME.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

    @BeforeEach
    void setUp() {
    }

    @Nested
    class CreateTests {

        @Test
        void create_ValidInput_CallsRepository() {
            // Arrange
            CampaignActivityInsertDTO input = createValidInsertDTO();
            MarketingCampaignModel campaign = createCampaign();
            CampaignActivityModel entity = createActivityEntity();

            when(campaignActivityMappers.inputToEntity(input)).thenReturn(entity);
            when(marketingCampaignRepository.findById(CAMPAIGN_ID)).thenReturn(Optional.of(campaign));
            when(campaignActivityRepository.save(any())).thenReturn(entity);
            when(campaignActivityMappers.entityToDTO(entity)).thenReturn(createDTO());

            // Act
            CampaignActivityDTO result = service.create(input);

            // Assert
            verify(campaignActivityMappers).inputToEntity(input);
            verify(marketingCampaignRepository).findById(CAMPAIGN_ID);
            verify(campaignActivityRepository).save(entity);
            verify(campaignActivityMappers).entityToDTO(entity);
            assertThat(result).isEqualTo(createDTO());
        }

        @Test
        void create_InvalidName_ThrowsException() {
            CampaignActivityInsertDTO input = createValidInsertDTO();
            input.setName("");

            assertThatThrownBy(() -> service.create(input))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("Activity name cannot be empty");
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void update_ExistingActivity_CallsMapper() {
            // Arrange
            CampaignActivityModel existing = createActivityEntity();
            CampaignActivityInsertDTO input = createValidInsertDTO();

            when(campaignActivityRepository.findById(ACTIVITY_ID)).thenReturn(Optional.of(existing));
            when(campaignActivityMappers.entityToDTO(existing)).thenReturn(createDTO());

            // Act
            CampaignActivityDTO result = service.update(ACTIVITY_ID, input);

            // Assert
            verify(campaignActivityMappers).updateEntity(existing, input);
            verify(campaignActivityRepository).save(existing);
            assertThat(result).isEqualTo(createDTO());
        }

        @Test
        void update_NonExistingActivity_ThrowsException() {
            when(campaignActivityRepository.findById(ACTIVITY_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.update(ACTIVITY_ID, createValidInsertDTO()))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Activity not found");
        }
    }

    @Nested
    class StatusTransitionTests {

        @Test
        void startActivity_ValidTransition_UpdatesStatus() {
            // Arrange
            CampaignActivityModel activity = createActivityEntity();
            activity.setStatus(ActivityStatus.PLANNED);

            when(campaignActivityRepository.findById(ACTIVITY_ID)).thenReturn(Optional.of(activity));
            when(campaignActivityMappers.entityToDTO(activity)).thenReturn(createDTO());

            // Act
            CampaignActivityDTO result = service.startActivity(ACTIVITY_ID);

            // Assert
            assertThat(activity.getStatus()).isEqualTo(ActivityStatus.IN_PROGRESS);
            assertThat(activity.getActualStartDate()).isNotNull();
            verify(campaignActivityRepository).save(activity);
        }

        @Test
        void startActivity_InvalidTransition_ThrowsException() {
            CampaignActivityModel activity = createActivityEntity();
            activity.setStatus(ActivityStatus.COMPLETED);

            when(campaignActivityRepository.findById(ACTIVITY_ID)).thenReturn(Optional.of(activity));

            assertThatThrownBy(() -> service.startActivity(ACTIVITY_ID))
                    .isInstanceOf(InvalidStatusTransitionException.class)
                    .hasMessageContaining("Cannot transition activity from COMPLETED to PLANNED");
        }
    }

    @Nested
    class BudgetCalculationTests {

        @Test
        void calculateRemainingBudget_WithActualCost_ReturnsDifference() {
            CampaignActivityModel activity = createActivityEntity();
            activity.setPlannedCost(BigDecimal.valueOf(1000));
            activity.setActualCost(BigDecimal.valueOf(750));

            when(campaignActivityRepository.findById(ACTIVITY_ID)).thenReturn(Optional.of(activity));

            BigDecimal result = service.calculateRemainingBudget(ACTIVITY_ID);

            assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(250));
        }

        @Test
        void calculateRemainingBudget_NullActualCost_ReturnsFullPlanned() {
            CampaignActivityModel activity = createActivityEntity();
            activity.setPlannedCost(BigDecimal.valueOf(1000));
            activity.setActualCost(null);

            when(campaignActivityRepository.findById(ACTIVITY_ID)).thenReturn(Optional.of(activity));

            BigDecimal result = service.calculateRemainingBudget(ACTIVITY_ID);

            assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(1000));
        }
    }


    private MarketingCampaignModel createCampaign() {
        return MarketingCampaignModel.builder()
                .id(CAMPAIGN_ID)
                .name("Test Campaign")
                .build();
    }

    private CampaignActivityModel createActivityEntity() {
        return CampaignActivityModel.builder()
                .id(ACTIVITY_ID)
                .name("Test Activity")
                .campaign(createCampaign())
                .plannedStartDate(NOW.plusDays(1))
                .plannedCost(BigDecimal.valueOf(1000))
                .status(ActivityStatus.PLANNED)
                .build();
    }

    private CampaignActivityDTO createDTO() {
        return CampaignActivityDTO.builder()
                .id(ACTIVITY_ID)
                .name("Test Activity")
                .description("This is a test activity")
                .activityType(ActivityType.EMAIL_BLAST)
                .plannedStartDate(FIXED_TIME.plusDays(1))
                .plannedEndDate(FIXED_TIME.plusDays(5))
                .actualStartDate(null)
                .actualEndDate(null)
                .status(ActivityStatus.PLANNED)
                .plannedCost(BigDecimal.valueOf(1000))
                .actualCost(null)
                .assignedTo("test_user")
                .successCriteria("Achieve 10% engagement")
                .targetAudience("Young professionals")
                .deliveryChannel("Email newsletter")
                .build();
    }

    private CampaignActivityInsertDTO createValidInsertDTO() {
        return CampaignActivityInsertDTO.builder()
                .name("Test Activity")
                .description("This is a test activity")
                .campaignId(CAMPAIGN_ID)
                .activityType(ActivityType.EMAIL_BLAST)
                .plannedStartDate(FIXED_TIME.plusDays(1))
                .plannedEndDate(FIXED_TIME.plusDays(5))
                .plannedCost(BigDecimal.valueOf(1000))
                .successCriteria("Achieve 10% engagement")
                .targetAudience("Young professionals")
                .deliveryChannel("Email newsletter")
                .build();
    }
}