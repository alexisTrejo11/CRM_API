package at.backend.MarketingCompany.MarketingCampaing.controller;

import static graphql.Assert.assertNotNull;
import static graphql.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import at.backend.MarketingCompany.common.utils.InputValidator;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MetricType;
import at.backend.MarketingCompany.marketing.metric.api.controller.CampaignMetricController;
import at.backend.MarketingCompany.marketing.metric.api.service.CampaignMetricService;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricDTO;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricInsertDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CampaignMetricControllerTest {

    @Mock
    private CampaignMetricService campaignMetricService;

    @InjectMocks
    private CampaignMetricController controller;

    @Mock
    private InputValidator validator;

    private UUID metricId;
    private CampaignMetricDTO metricDTO;
    private CampaignMetricInsertDTO insertDTO;

    @BeforeEach
    void setUp() {
        metricId = UUID.randomUUID();


        metricDTO = CampaignMetricDTO.builder()
                .id(metricId)
                .campaignId(UUID.randomUUID())
                .name("Test Metric")
                .description("This is a test metric")
                .type(MetricType.COUNT)
                .value(BigDecimal.valueOf(100.5))
                .targetValue(BigDecimal.valueOf(200.0))
                .measurementUnit("%")
                .lastCalculated(LocalDateTime.now())
                .calculationFormula("(clicks / impressions) * 100")
                .dataSource("Google Analytics")
                .automated(true)
                .build();

        insertDTO = CampaignMetricInsertDTO.builder()
                .campaignId(UUID.randomUUID())
                .name("Test Metric")
                .description("This is a test metric")
                .type(MetricType.PERCENTAGE)
                .value(BigDecimal.valueOf(100.5))
                .targetValue(BigDecimal.valueOf(200.0))
                .measurementUnit("%")
                .calculationFormula("(clicks / impressions) * 100")
                .dataSource("Google Analytics")
                .automated(true)
                .build();
    }

    @Nested
    class GetMetricByIdTests {

        @Test
        void getMetricById_ShouldReturnMetric() {
            // Arrange
            when(campaignMetricService.getById(metricId)).thenReturn(metricDTO);

            // Act
            CampaignMetricDTO result = controller.getMetricById(metricId);

            // Assert
            assertNotNull(result);
            assertEquals(metricId, result.getId());
            assertEquals("Test Metric", result.getName());
            verify(campaignMetricService).getById(metricId);
        }

        @Test
        void getMetricById_NonExistingMetric_ThrowsException() {
            // Arrange
            when(campaignMetricService.getById(metricId)).thenThrow(new RuntimeException("Metric not found"));

            // Act & Assert
            assertThatThrownBy(() -> controller.getMetricById(metricId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Metric not found");
        }
    }

    @Nested
    class CreateMetricTests {

        @Test
        void createMetric_ValidInput_CreatesAndReturnsMetric() {
            // Arrange
            when(campaignMetricService.create(any(CampaignMetricInsertDTO.class))).thenReturn(metricDTO);

            // Act
            CampaignMetricDTO result = controller.createMetric(insertDTO);

            // Assert
            assertNotNull(result);
            assertEquals(metricId, result.getId());
            assertEquals("Test Metric", result.getName());
            verify(campaignMetricService).create(insertDTO);
        }

        @Test
        void createMetric_InvalidInput_ThrowsException() {
            // Arrange
            CampaignMetricInsertDTO invalidDTO = new CampaignMetricInsertDTO();
            invalidDTO.setName("");

            doThrow(new ValidationException("Name cannot be empty"))
                    .when(validator).validate(any());

            // Act & Assert
            assertThatThrownBy(() -> controller.createMetric(invalidDTO))
                    .isInstanceOf(ValidationException.class);
        }
    }

    @Nested
    class UpdateMetricTests {

        @Test
        void updateMetric_ValidInput_UpdatesAndReturnsMetric() {
            // Arrange
            when(campaignMetricService.update(eq(metricId), any(CampaignMetricInsertDTO.class))).thenReturn(metricDTO);

            // Act
            CampaignMetricDTO result = controller.updateMetric(insertDTO, metricId);

            // Assert
            assertNotNull(result);
            assertEquals(metricId, result.getId());
            assertEquals("Test Metric", result.getName());
            verify(campaignMetricService).update(metricId, insertDTO);
        }

        @Test
        void updateMetric_NonExistingMetric_ThrowsException() {
            // Arrange
            when(campaignMetricService.update(eq(metricId), any(CampaignMetricInsertDTO.class)))
                    .thenThrow(new RuntimeException("Metric not found"));

            // Act & Assert
            assertThatThrownBy(() -> controller.updateMetric(insertDTO, metricId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Metric not found");
        }
    }

    @Nested
    class DeleteMetricTests {

        @Test
        void deleteMetric_ExistingMetric_DeletesAndReturnsTrue() {
            // Arrange
            doNothing().when(campaignMetricService).delete(metricId);

            // Act
            boolean result = controller.deleteMetric(metricId);

            // Assert
            assertTrue(result);
            verify(campaignMetricService).delete(metricId);
        }

        @Test
        void deleteMetric_NonExistingMetric_ThrowsException() {
            // Arrange
            doThrow(new RuntimeException("Metric not found")).when(campaignMetricService).delete(metricId);

            // Act & Assert
            assertThatThrownBy(() -> controller.deleteMetric(metricId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Metric not found");
        }
    }
}