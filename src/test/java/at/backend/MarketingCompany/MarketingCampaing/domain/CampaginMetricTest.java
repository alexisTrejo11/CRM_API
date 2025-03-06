package at.backend.MarketingCompany.MarketingCampaing.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.mock;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MetricType;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import at.backend.MarketingCompany.marketing.metric.domain.CampaignMetric;
import at.backend.MarketingCompany.marketing.metric.domain.CampaignMetricId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CampaignMetricTest {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private MarketingCampaign mockCampaign;
    private CampaignMetric metric;

    @BeforeEach
    void setUp() {
        mockCampaign = mock(MarketingCampaign.class);
        metric = createBaseMetric();
    }

    private CampaignMetric createBaseMetric() {
        return new CampaignMetric(
                CampaignMetricId.generate(),
                mockCampaign,
                "Test Metric",
                MetricType.CURRENCY,
                NOW
        );
    }

    @Nested
    class ConstructorTests {

        @Test
        void constructor_SetsRequiredFields() {
            assertThat(metric.getId()).isNotNull();
            assertThat(metric.getCampaign()).isEqualTo(mockCampaign);
            assertThat(metric.getName()).isEqualTo("Test Metric");
            assertThat(metric.getType()).isEqualTo(MetricType.CURRENCY);
            assertThat(metric.getCreatedAt()).isEqualTo(NOW);
            assertThat(metric.getUpdatedAt()).isEqualTo(NOW);
        }

        @Test
        void constructor_InitializesOptionalFields() {
            assertThat(metric.getDescription()).isNull();
            assertThat(metric.getValue()).isNull();
            assertThat(metric.getTargetValue()).isNull();
            assertThat(metric.isAutomated()).isFalse();
        }
    }

    @Nested
    class UpdateValueTests {

        @Test
        void updateValue_SetsValueAndTimestamps() {
            BigDecimal newValue = BigDecimal.valueOf(150);
            metric.updateValue(newValue);

            assertThat(metric.getValue()).isEqualByComparingTo(newValue);
            assertThat(metric.getLastCalculated()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
            assertThat(metric.getUpdatedAt()).isAfter(NOW);
        }
    }

    @Nested
    class IsTargetAchievedTests {

        @Test
        void isTargetAchieved_NullValues_ReturnsFalse() {
            assertThat(metric.isTargetAchieved()).isFalse();
        }

        @Test
        void isTargetAchieved_CurrencyType_Achieved() {
            metric.setTargetValue(BigDecimal.valueOf(100));
            metric.updateValue(BigDecimal.valueOf(150));

            assertThat(metric.isTargetAchieved()).isTrue();
        }

        @Test
        void isTargetAchieved_CostType_NotAchieved() {
            metric.setType(MetricType.COST);
            metric.setTargetValue(BigDecimal.valueOf(100));
            metric.updateValue(BigDecimal.valueOf(150));

            assertThat(metric.isTargetAchieved()).isFalse();
        }

        @Test
        void isTargetAchieved_PercentageType_EqualValue() {
            metric.setType(MetricType.PERCENTAGE);
            metric.setTargetValue(BigDecimal.valueOf(50));
            metric.updateValue(BigDecimal.valueOf(50));

            assertThat(metric.isTargetAchieved()).isTrue();
        }
    }

    @Nested
    class CalculatePerformanceRatioTests {

        @Test
        void calculatePerformanceRatio_ZeroTarget_ReturnsZero() {
            metric.setTargetValue(BigDecimal.ZERO);
            assertThat(metric.calculatePerformanceRatio()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        void calculatePerformanceRatio_ValidValues_ReturnsCorrectRatio() {
            metric.setTargetValue(BigDecimal.valueOf(200));
            metric.updateValue(BigDecimal.valueOf(150));

            BigDecimal expected = BigDecimal.valueOf(75.00); // (150/200)*100
            BigDecimal actual = metric.calculatePerformanceRatio();

            assertThat(actual)
                    .isEqualByComparingTo(expected);

            assertThat(actual.scale()).isEqualTo(2);
        }
    }

    @Nested
    class MarkAsAutomatedTests {

        @Test
        void markAsAutomated_UpdatesFlagAndTimestamp() {
            metric.markAsAutomated();

            assertThat(metric.isAutomated()).isTrue();
            assertThat(metric.getUpdatedAt()).isAfter(NOW);
        }
    }

}