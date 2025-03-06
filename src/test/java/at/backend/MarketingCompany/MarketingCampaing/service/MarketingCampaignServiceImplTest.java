package at.backend.MarketingCompany.MarketingCampaing.service;

import at.backend.MarketingCompany.common.exceptions.CampaignServiceException;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignStatus;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignType;
import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.CampaignId;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignRepository;
import at.backend.MarketingCompany.marketing.campaign.api.service.MarketingCampaignServiceImpl;
import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.*;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.SuccessCriteriaDetailsDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.TargetAudienceDetailsDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers.CampaignMappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarketingCampaignServiceImplTest {

    @Mock
    private CampaignMappers mappers;

    @Mock
    private MarketingCampaignRepository campaignRepository;

    @InjectMocks
    private MarketingCampaignServiceImpl service;

    private UUID campaignId;
    private MarketingCampaignModel campaignModel;
    private MarketingCampaign campaignDomain;
    private MarketingCampaignDTO campaignDTO;

    @BeforeEach
    void setUp() {
        campaignId = UUID.randomUUID();
        campaignModel = MarketingCampaignModel.builder()
                .id(campaignId)
                .name("Test Campaign")
                .description("Test Description")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(10))
                .budget(new BigDecimal("1000"))
                .costToDate(BigDecimal.ZERO)
                .status(CampaignStatus.DRAFT)
                .type(CampaignType.EMAIL)
                .targetAudience("Test Audience")
                .successCriteria("Test Criteria")
                .build();

        campaignDomain = MarketingCampaign.builder()
                .id(CampaignId.of(campaignId))
                .name("Test Campaign")
                .description("Test Description")
                .period(new CampaignPeriod(LocalDate.now().plusDays(1), LocalDate.now().plusDays(10)))
                .budget(new Budget(new BigDecimal("1000"), BigDecimal.ZERO))
                .status(CampaignStatus.DRAFT)
                .type(CampaignType.EMAIL)
                .targetAudience(new TargetAudience("Test Audience", Set.of("Demo"), Set.of("Location"), Set.of("Interest")))
                .successCriteria(new SuccessCriteria("Test Criteria", Map.of("Metric", 1.0)))
                .build();

        campaignDTO = MarketingCampaignDTO.builder()
                .id(campaignId)
                .name("Test Campaign")
                .description("Test Description")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(10))
                .budget(new BigDecimal("1000"))
                .costToDate(BigDecimal.ZERO)
                .status(CampaignStatus.PAUSED)
                .type(CampaignType.EMAIL)
                .targetAudience("Test Audience")
                .successCriteria("Test Criteria")
                .build();
    }

    @Test
    void testGetActiveCampaigns() {
        // Arrange
        List<MarketingCampaignModel> activeModels = List.of(campaignModel);
        when(campaignRepository.findByStatus(CampaignStatus.ACTIVE)).thenReturn(activeModels);
        when(mappers.modelToDTO(campaignModel)).thenReturn(campaignDTO);

        // Act
        List<MarketingCampaignDTO> result = service.getActiveCampaigns(LocalDate.now());

        // Assert
        assertEquals(1, result.size());
        assertEquals(campaignDTO, result.getFirst());
    }

    @Test
    void testStartCampaign() {
        // Arrange
        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaignModel));
        MarketingCampaign spyCampaign = spy(campaignDomain);
        when(mappers.modelToDomain(campaignModel)).thenReturn(spyCampaign);
        when(mappers.domainToDTO(any(MarketingCampaign.class))).thenReturn(campaignDTO);
        when(mappers.domainToModel(any(MarketingCampaign.class))).thenReturn(campaignModel);

        // Act
        spyCampaign.setPeriod(new CampaignPeriod(LocalDate.now().minusDays(1), LocalDate.now().plusDays(10)));
        MarketingCampaignDTO result = service.startCampaign(campaignId);

        // Assert
        verify(spyCampaign).startCampaign();
        verify(campaignRepository).save(any(MarketingCampaignModel.class));
        assertEquals(campaignDTO, result);
    }

    @Test
    void testCompleteCampaign() {
        // Arrange
        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaignModel));
        MarketingCampaign spyCampaign = spy(campaignDomain);
        when(mappers.modelToDomain(campaignModel)).thenReturn(spyCampaign);
        when(mappers.domainToDTO(any(MarketingCampaign.class))).thenReturn(campaignDTO);
        when(mappers.domainToModel(any(MarketingCampaign.class))).thenReturn(campaignModel);

        // Act
        spyCampaign.setStatus(CampaignStatus.ACTIVE);
        spyCampaign.setPeriod(new CampaignPeriod(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1)));
        MarketingCampaignDTO result = service.completeCampaign(campaignId);

        // Assert
        verify(spyCampaign).completeCampaign();
        verify(campaignRepository).save(any(MarketingCampaignModel.class));
        assertEquals(campaignDTO, result);
    }

    @Test
    void testUpdateBudget() {
        // Arrange
        BigDecimal newBudget = new BigDecimal("500");
        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaignModel));
        MarketingCampaign spyCampaign = spy(campaignDomain);
        when(mappers.modelToDomain(campaignModel)).thenReturn(spyCampaign);
        when(mappers.domainToDTO(any(MarketingCampaign.class))).thenReturn(campaignDTO);
        when(mappers.domainToModel(any(MarketingCampaign.class))).thenReturn(campaignModel);


        // Act
        MarketingCampaignDTO result = service.updateBudget(campaignId, newBudget);

        // Assert
        verify(spyCampaign).addCost(newBudget);
        verify(campaignRepository).save(any(MarketingCampaignModel.class));
        assertEquals(campaignDTO, result);
    }

    @Test
    void testCalculateCampaignROI() {
        // Arrange
        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaignModel));
        MarketingCampaign spyCampaign = spy(campaignDomain);
        doReturn(2.5).when(spyCampaign).calculateROI();
        when(mappers.modelToDomain(campaignModel)).thenReturn(spyCampaign);

        // Act
        double roi = service.calculateCampaignROI(campaignId);

        // Assert
        assertEquals(2.5, roi);
    }

    @Test
    void testArchiveCampaign() {
        // Arrange
        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaignModel));
        MarketingCampaign spyCampaign = spy(campaignDomain);
        spyCampaign.setStatus(CampaignStatus.ANALYZING);

        when(mappers.modelToDomain(campaignModel)).thenReturn(spyCampaign);
        when(mappers.domainToModel(any(MarketingCampaign.class))).thenReturn(campaignModel);

        // Act
        service.archiveCampaign(campaignId);

        // Assert
        verify(spyCampaign).archive();
        verify(campaignRepository).save(any(MarketingCampaignModel.class));
    }

    @Test
    void testGetAll() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Page<MarketingCampaignModel> page = new PageImpl<>(List.of(campaignModel));
        when(campaignRepository.findAll(pageable)).thenReturn(page);
        when(mappers.modelToDTO(campaignModel)).thenReturn(campaignDTO);

        // Act
        Page<MarketingCampaignDTO> result = service.getAll(pageable);

        // Assert
        assertEquals(1, result.getContent().size());
        assertEquals(campaignDTO, result.getContent().getFirst());
    }

    @Test
    void testGetById() {
        // Arrange
        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaignModel));
        when(mappers.modelToDomain(campaignModel)).thenReturn(campaignDomain);
        when(mappers.domainToDTO(campaignDomain)).thenReturn(campaignDTO);

        // Act
        MarketingCampaignDTO result = service.getById(campaignId);

        // Assert
        assertEquals(campaignDTO, result);
    }

    @Test
    void testCreateCampaign() {
        // Arrange
        MarketingCampaignInsertDTO insertDTO = MarketingCampaignInsertDTO.builder()
                .name("New Campaign")
                .description("New Description")
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(12))
                .budget(new BigDecimal("2000"))
                .targetAudience(new TargetAudienceDetailsDTO("Audience", Set.of("Demographic"), Set.of("Location"), Set.of("Interest")))
                .successCriteria(new SuccessCriteriaDetailsDTO("Criteria", Map.of("Metric", 2.0)))
                .type(CampaignType.TV)
                .build();

        TargetAudience ta = new TargetAudience("Audience", Set.of("Demographic"), Set.of("Location"), Set.of("Interest"));
        SuccessCriteria sc = new SuccessCriteria("Criteria", Map.of("Metric", 2.0));

        when(mappers.toTargetAudience(insertDTO.getTargetAudience())).thenReturn(ta);
        when(mappers.toSuccessCriteria(insertDTO.getSuccessCriteria())).thenReturn(sc);
        when(mappers.domainToDTO(any(MarketingCampaign.class))).thenAnswer(invocation -> {
            MarketingCampaign camp = invocation.getArgument(0);
            return MarketingCampaignDTO.builder()
                    .id(camp.getId().getValue())
                    .name(camp.getName())
                    .description(camp.getDescription())
                    .startDate(camp.getPeriod().startDate())
                    .endDate(camp.getPeriod().endDate())
                    .budget(camp.getBudget().totalBudget())
                    .costToDate(camp.getBudget().costToDate())
                    .status(camp.getStatus())
                    .type(camp.getType())
                    .targetAudience(camp.getTargetAudience().toString())
                    .successCriteria(camp.getSuccessCriteria().toString())
                    .build();
        });

        // Act
        MarketingCampaignDTO result = service.create(insertDTO);

        // Assert
        assertNotNull(result);
        assertEquals("New Campaign", result.getName());
        verify(campaignRepository).save(any(MarketingCampaignModel.class));
    }

    @Test
    void testUpdateCampaign() {
        // Arrange
        MarketingCampaignInsertDTO insertDTO = MarketingCampaignInsertDTO.builder()
                .name("Updated Campaign")
                .description("Updated Description")
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(12))
                .budget(new BigDecimal("3000"))
                .targetAudience(new TargetAudienceDetailsDTO("Audience", Set.of("Demographic"), Set.of("Location"), Set.of("Interest")))
                .successCriteria(new SuccessCriteriaDetailsDTO("Criteria", Map.of("Metric", 3.0)))
                .type(CampaignType.EMAIL)
                .build();

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaignModel));
        when(mappers.modelToDomain(campaignModel)).thenReturn(campaignDomain);
        when(mappers.domainToDTO(campaignDomain)).thenReturn(campaignDTO);
        when(mappers.domainToModel(any(MarketingCampaign.class))).thenReturn(campaignModel);


        // Act
        MarketingCampaignDTO result = service.update(campaignId, insertDTO);

        // Assert
        verify(campaignRepository).save(any(MarketingCampaignModel.class));
        assertEquals(campaignDTO, result);
    }

    @Test
    void testDeleteCampaign() {
        // Arrange
        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaignModel));
        when(mappers.modelToDomain(campaignModel)).thenReturn(campaignDomain);

        // Act
        service.delete(campaignId);

        // Assert
        verify(campaignRepository).deleteById(campaignId);
    }

    @Test
    void testValidateCampaign_InsertDTO_InvalidName() {
        MarketingCampaignInsertDTO insertDTO = MarketingCampaignInsertDTO.builder()
                .name("   ")
                .description("Desc")
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(12))
                .budget(new BigDecimal("1000"))
                .targetAudience(new TargetAudienceDetailsDTO("Audience", Set.of("Demo"), Set.of("Loc"), Set.of("Int")))
                .successCriteria(new SuccessCriteriaDetailsDTO("Criteria", Map.of("Metric", 1.0)))
                .type(CampaignType.EMAIL)
                .build();

        // Act & Assert
        assertThrows(CampaignServiceException.class, () -> service.validate(insertDTO));
    }

    @Test
    void testValidateCampaign_InsertDTO_PastStartDate() {
        MarketingCampaignInsertDTO insertDTO = MarketingCampaignInsertDTO.builder()
                .name("Valid Name")
                .description("Desc")
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(12))
                .budget(new BigDecimal("1000"))
                .targetAudience(new TargetAudienceDetailsDTO("Audience", Set.of("Demo"), Set.of("Loc"), Set.of("Int")))
                .successCriteria(new SuccessCriteriaDetailsDTO("Criteria", Map.of("Metric", 1.0)))
                .type(CampaignType.EMAIL)
                .build();

        // Act & Assert
        assertThrows(CampaignServiceException.class, () -> service.validate(insertDTO));
    }

    @Test
    void testValidateCampaign_InsertDTO_NegativeBudget() {
        MarketingCampaignInsertDTO insertDTO = MarketingCampaignInsertDTO.builder()
                .name("Valid Name")
                .description("Desc")
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(12))
                .budget(new BigDecimal("-500"))
                .targetAudience(new TargetAudienceDetailsDTO("Audience", Set.of("Demo"), Set.of("Loc"), Set.of("Int")))
                .successCriteria(new SuccessCriteriaDetailsDTO("Criteria", Map.of("Metric", 1.0)))
                .type(CampaignType.EMAIL)
                .build();

        assertThrows(CampaignServiceException.class, () -> service.validate(insertDTO));
    }
}

