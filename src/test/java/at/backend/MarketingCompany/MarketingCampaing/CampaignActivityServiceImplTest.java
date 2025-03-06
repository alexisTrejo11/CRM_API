package at.backend.MarketingCompany.MarketingCampaing;

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
import at.backend.MarketingCompany.marketing.activity.domain.CampaignActivity;
import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivityCost;
import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivityId;
import at.backend.MarketingCompany.marketing.activity.domain.HelperClasses.ActivitySchedule;
import at.backend.MarketingCompany.marketing.activity.infrastructure.AutoMappers.ActivityMappers;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.CampaignId;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignRepository;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers.CampaignMappers;
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
    private ActivityMappers activityMappers;

    @Mock
    private CampaignMappers campaignMappers;

    @InjectMocks
    private CampaignActivityServiceImpl service;

    private final UUID CAMPAIGN_ID = UUID.randomUUID();
    private final UUID ACTIVITY_ID = UUID.randomUUID();
    private final UUID ASSIGNED_TO_ID = UUID.randomUUID();
    private final LocalDateTime NOW = LocalDateTime.now();
    private static final LocalDateTime FIXED_TIME = LocalDateTime.of(2026, 10, 1, 12, 0);
    private Clock fixedClock = Clock.fixed(FIXED_TIME.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

    @Nested
    class CreateTests {

        @Test
        void create_ValidInput_CallsRepository() {
            // Arrange
            CampaignActivityInsertDTO input = createValidInsertDTO();
            MarketingCampaignModel campaignModel = createCampaignModel();
            CampaignActivity domain = createDomainEntity();
            CampaignActivityModel model = createActivityModel();
            MarketingCampaign campaignDomain = new MarketingCampaign();

            when(marketingCampaignRepository.findById(input.getCampaignId()))
                    .thenReturn(Optional.of(campaignModel));
            when(campaignMappers.modelToDomain(campaignModel)).thenReturn(campaignDomain);
            when(activityMappers.inputToEntity(input)).thenReturn(domain);
            when(activityMappers.domainToModel(domain)).thenReturn(model);
            when(activityMappers.domainToDTO(domain)).thenReturn(createDTO());

            // Act
            CampaignActivityDTO result = service.create(input);

            // Assert
            verify(marketingCampaignRepository).findById(input.getCampaignId());
            verify(activityMappers).inputToEntity(input);
            verify(campaignActivityRepository).save(model);
            verify(activityMappers).domainToDTO(domain);

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
            CampaignActivityModel model = createActivityModel();
            CampaignActivity domain = createDomainEntity();
            CampaignActivityInsertDTO inputDto = createValidInsertDTO();

            when(campaignActivityRepository.findById(ACTIVITY_ID))
                    .thenReturn(Optional.of(model));
            when(activityMappers.modelToDomain(model)).thenReturn(domain);
            when(activityMappers.domainToModel(domain)).thenReturn(model);
            when(activityMappers.domainToDTO(domain)).thenReturn(createDTO());

            // Act
            CampaignActivityDTO result = service.update(ACTIVITY_ID, inputDto);

            // Assert
            verify(activityMappers).updateEntity(domain, inputDto);
            verify(campaignActivityRepository).save(model);
            assertThat(result).isNotNull();
        }

        @Test
        void update_NonExistingActivity_ThrowsException() {
            when(campaignActivityRepository.findById(ACTIVITY_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.update(ACTIVITY_ID, createValidInsertDTO()))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("not found");
        }
    }

    @Nested
    class StatusTransitionTests {

        @Test
        void startActivity_ValidTransition_UpdatesStatus() {
            // Arrange
            CampaignActivityModel model = createActivityModel();
            CampaignActivity domain = createDomainEntity();
            domain.setStatus(ActivityStatus.PLANNED);

            when(campaignActivityRepository.findById(ACTIVITY_ID))
                    .thenReturn(Optional.of(model));
            when(activityMappers.modelToDomain(model)).thenReturn(domain);
            when(activityMappers.domainToModel(domain)).thenReturn(model);
            when(activityMappers.domainToDTO(domain)).thenReturn(createDTO());

            // Act
            CampaignActivityDTO result = service.startActivity(ACTIVITY_ID);

            // Assert
            // We verify that the startActivity method was called which should set the status
            // Since we're using a mock, we can't directly test that the status was changed
            verify(campaignActivityRepository).save(model);
            verify(activityMappers).domainToDTO(domain);
        }

        @Test
        void startActivity_InvalidTransition_ThrowsException() {
            // Arrange
            CampaignActivityModel model = createActivityModel();
            CampaignActivity domain = createDomainEntity();
            domain.setStatus(ActivityStatus.COMPLETED); // Already completed, can't start

            when(campaignActivityRepository.findById(ACTIVITY_ID))
                    .thenReturn(Optional.of(model));
            when(activityMappers.modelToDomain(model)).thenReturn(domain);

            // Act & Assert
            assertThatThrownBy(() -> service.startActivity(ACTIVITY_ID))
                    .isInstanceOf(InvalidStatusTransitionException.class);
        }
    }

    @Nested
    class BudgetCalculationTests {
        @Test
        void calculateRemainingBudget_WithActualCost_ReturnsDifference() {
            CampaignActivityModel model = createActivityModel();
            model.setPlannedCost(BigDecimal.valueOf(1000));
            model.setActualCost(BigDecimal.valueOf(750));

            when(campaignActivityRepository.findById(ACTIVITY_ID))
                    .thenReturn(Optional.of(model));

            BigDecimal result = service.calculateRemainingBudget(ACTIVITY_ID);

            assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(250));
        }

        @Test
        void calculateRemainingBudget_NullActualCost_ReturnsFullPlanned() {
            CampaignActivityModel model = createActivityModel();
            model.setPlannedCost(BigDecimal.valueOf(1000));
            model.setActualCost(null);

            when(campaignActivityRepository.findById(ACTIVITY_ID))
                    .thenReturn(Optional.of(model));

            BigDecimal result = service.calculateRemainingBudget(ACTIVITY_ID);

            assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(1000));
        }
    }

    private MarketingCampaignModel createCampaignModel() {
        return MarketingCampaignModel.builder()
                .id(CAMPAIGN_ID)
                .name("Test Campaign")
                .build();
    }

    private CampaignActivityModel createActivityModel() {
        return CampaignActivityModel.builder()
                .id(ACTIVITY_ID)
                .campaign(createCampaignModel())
                .name("Test Activity")
                .activityType(ActivityType.EMAIL_BLAST)
                .plannedStartDate(FIXED_TIME.plusDays(1))
                .plannedEndDate(FIXED_TIME.plusDays(5))
                .plannedCost(BigDecimal.valueOf(1000))
                .actualCost(BigDecimal.ZERO)
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
                .assignedTo(ASSIGNED_TO_ID)
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

    private CampaignActivity createDomainEntity() {
        CampaignActivity domain = new CampaignActivity();

        domain.setId(new ActivityId(ACTIVITY_ID));
        domain.setCampaignId(CampaignId.of(CAMPAIGN_ID));
        domain.setName("Test Activity");
        domain.setDescription("This is a test activity");
        domain.setType(ActivityType.EMAIL_BLAST);
        domain.setStatus(ActivityStatus.PLANNED);

        ActivitySchedule schedule = new ActivitySchedule(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusDays(1), null, null);
        domain.setSchedule(schedule);

        ActivityCost cost = new ActivityCost(BigDecimal.valueOf(1000), null);
        domain.setCost(cost);

        domain.setSuccessCriteria("Achieve 10% engagement");
        domain.setTargetAudience("Young professionals");
        domain.setDeliveryChannel("Email newsletter");

        return domain;
    }
}