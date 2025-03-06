package at.backend.MarketingCompany.MarketingCampaing.controller;

import at.backend.MarketingCompany.common.exceptions.BusinessLogicException;
import at.backend.MarketingCompany.common.exceptions.InvalidInputException;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MetricType;
import at.backend.MarketingCompany.marketing.metric.api.controller.CampaignMetricController;
import at.backend.MarketingCompany.marketing.metric.api.service.CampaignMetricService;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricDTO;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricInsertDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@GraphQlTest(CampaignMetricController.class)
@Import(CampaignMetricControllerTestConfig.class)
class CampaignMetricControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private CampaignMetricService campaignMetricService;

    private UUID metricId;
    private UUID campaignId;
    private CampaignMetricDTO metricDTO;
    private CampaignMetricInsertDTO insertDTO;

    @BeforeEach
    void setUp() {
        metricId = UUID.randomUUID();
        campaignId = UUID.randomUUID();

        // Setup DTO for responses
        metricDTO = CampaignMetricDTO.builder()
                .id(metricId)
                .campaignId(campaignId)
                .name("Test Metric")
                .description("Test Description")
                .type(MetricType.COST)
                .value(BigDecimal.valueOf(100))
                .targetValue(BigDecimal.valueOf(150))
                .measurementUnit("count")
                .lastCalculated(LocalDateTime.now())
                .calculationFormula("Some formula")
                .dataSource("Google Analytics")
                .automated(true)
                .build();

        // Setup insert DTO for mutations
        insertDTO = new CampaignMetricInsertDTO();
        insertDTO.setCampaignId(campaignId);
        insertDTO.setName("Test Metric");
        insertDTO.setDescription("Test Description");
        insertDTO.setType(MetricType.COST);
        insertDTO.setValue(BigDecimal.valueOf(100));
        insertDTO.setTargetValue(BigDecimal.valueOf(150));
        insertDTO.setMeasurementUnit("count");
        insertDTO.setCalculationFormula("Some formula");
        insertDTO.setDataSource("Google Analytics");
        insertDTO.setAutomated(true);
    }

    @Test
    void getMetricById_ReturnsMetric() {
        // Arrange
        when(campaignMetricService.getById(metricId)).thenReturn(metricDTO);

        // Act and Assert
        String document = """
                query {
                  getMetricById(id: "%s") {
                    id
                    campaignId
                    name
                    description
                    type
                    value
                    targetValue
                    measurementUnit
                    lastCalculated
                    calculationFormula
                    dataSource
                    automated
                  }
                }
                """.formatted(metricId);

        graphQlTester.document(document)
                .execute()
                .path("getMetricById")
                .entity(CampaignMetricDTO.class)
                .isEqualTo(metricDTO);

        verify(campaignMetricService).getById(metricId);
    }

    @Test
    void getMetricById_NotFound_ThrowsException() {
        // Arrange
        when(campaignMetricService.getById(metricId)).thenThrow(new EntityNotFoundException("Metric not found"));

        // Act and Assert
        String document = """
                query {
                  getMetricById(id: "%s") {
                    id
                    name
                  }
                }
                """.formatted(metricId);

        graphQlTester.document(document)
                .execute()
                .errors()
                .expect(error -> Objects.requireNonNull(error.getMessage()).contains("Metric not found"));

        verify(campaignMetricService).getById(metricId);
    }

    @Test
    void createMetric_ValidInput_ReturnsCreatedMetric() {
        // Arrange
        when(campaignMetricService.create(any(CampaignMetricInsertDTO.class))).thenReturn(metricDTO);

        // Act and Assert
        String document = """
                mutation {
                  createMetric(input: {
                    campaignId: "%s",
                    name: "Test Metric",
                    description: "Test Description",
                    type: ENGAGEMENT,
                    value: 100,
                    targetValue: 150,
                    measurementUnit: "count",
                    calculationFormula: "Some formula",
                    dataSource: "Google Analytics",
                    automated: true
                  }) {
                    id
                    campaignId
                    name
                    type
                    value
                    targetValue
                  }
                }
                """.formatted(campaignId);

        graphQlTester.document(document)
                .execute()
                .path("createMetric")
                .entity(CampaignMetricDTO.class)
                .satisfies(dto -> {
                    assertEquals(metricId, dto.getId());
                    assertEquals(campaignId, dto.getCampaignId());
                    assertEquals("Test Metric", dto.getName());
                    assertEquals(MetricType.COST, dto.getType());
                    assertEquals(0, BigDecimal.valueOf(100).compareTo(dto.getValue()));
                    assertEquals(0, BigDecimal.valueOf(150).compareTo(dto.getTargetValue()));
                });

        verify(campaignMetricService).create(any(CampaignMetricInsertDTO.class));
    }

    @Test
    void createMetric_InvalidInput_ThrowsException() {
        // Arrange
        when(campaignMetricService.create(any(CampaignMetricInsertDTO.class)))
                .thenThrow(new InvalidInputException("Name cannot be empty"));

        // Act and Assert
        String document = """
                mutation {
                  createMetric(input: {
                    campaignId: "%s",
                    name: "",
                    type: ENGAGEMENT,
                    value: 100,
                    targetValue: 150
                  }) {
                    id
                    name
                  }
                }
                """.formatted(campaignId);

        graphQlTester.document(document)
                .execute()
                .errors()
                .expect(error -> Objects.requireNonNull(error.getMessage()).contains("Name cannot be empty"));

        verify(campaignMetricService).create(any(CampaignMetricInsertDTO.class));
    }

    @Test
    void updateMetric_ValidInput_ReturnsUpdatedMetric() {
        // Arrange
        when(campaignMetricService.update(eq(metricId), any(CampaignMetricInsertDTO.class)))
                .thenReturn(metricDTO);

        // Act and Assert
        String document = """
                mutation {
                  updateMetric(
                    id: "%s",
                    input: {
                      campaignId: "%s",
                      name: "Test Metric",
                      description: "Updated Description",
                      type: ENGAGEMENT,
                      value: 120,
                      targetValue: 180,
                      measurementUnit: "count",
                      calculationFormula: "Updated formula",
                      dataSource: "Google Analytics",
                      automated: true
                    }
                  ) {
                    id
                    campaignId
                    name
                    description
                    value
                    targetValue
                  }
                }
                """.formatted(metricId, campaignId);

        graphQlTester.document(document)
                .execute()
                .path("updateMetric")
                .entity(CampaignMetricDTO.class)
                .satisfies(dto -> {
                    assertEquals(metricId, dto.getId());
                    assertEquals(campaignId, dto.getCampaignId());
                    assertEquals("Test Metric", dto.getName());
                });

        verify(campaignMetricService).update(eq(metricId), any(CampaignMetricInsertDTO.class));
    }

    @Test
    void updateMetric_MetricNotFound_ThrowsException() {
        // Arrange
        when(campaignMetricService.update(eq(metricId), any(CampaignMetricInsertDTO.class)))
                .thenThrow(new EntityNotFoundException("Metric not found"));

        // Act and Assert
        String document = """
                mutation {
                  updateMetric(
                    id: "%s",
                    input: {
                      campaignId: "%s",
                      name: "Test Metric",
                      type: ENGAGEMENT,
                      value: 100,
                      targetValue: 150
                    }
                  ) {
                    id
                    name
                  }
                }
                """.formatted(metricId, campaignId);

        graphQlTester.document(document)
                .execute()
                .errors()
                .expect(error -> Objects.requireNonNull(error.getMessage()).contains("Metric not found"));

        verify(campaignMetricService).update(eq(metricId), any(CampaignMetricInsertDTO.class));
    }

    @Test
    void deleteMetric_ValidId_ReturnsTrue() {
        // Arrange
        doNothing().when(campaignMetricService).delete(metricId);

        // Act and Assert
        String document = """
                mutation {
                  deleteMetric(id: "%s")
                }
                """.formatted(metricId);

        graphQlTester.document(document)
                .execute()
                .path("deleteMetric")
                .entity(Boolean.class)
                .isEqualTo(true);

        verify(campaignMetricService).delete(metricId);
    }

    @Test
    void deleteMetric_BusinessConstraint_ThrowsException() {
        // Arrange
        doThrow(new BusinessLogicException("Cannot delete metric with dependent data"))
                .when(campaignMetricService).delete(metricId);

        // Act and Assert
        String document = """
                mutation {
                  deleteMetric(id: "%s")
                }
                """.formatted(metricId);

        graphQlTester.document(document)
                .execute()
                .errors()
                .expect(error -> Objects.requireNonNull(error.getMessage()).contains("Cannot delete metric with dependent data"));

        verify(campaignMetricService).delete(metricId);
    }
}