package at.backend.MarketingCompany.marketing.metric.domain;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MetricType;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class CampaignMetric {

    private final CampaignMetricId id;
    @Setter
    private MarketingCampaign campaign;
    private final String name;
    private final MetricType type;
    private String description;
    private BigDecimal value;
    private BigDecimal targetValue;
    private String measurementUnit;
    private LocalDateTime lastCalculated;
    private String calculationFormula;
    private String dataSource;
    private boolean automated;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CampaignMetric(
            CampaignMetricId id,
            MarketingCampaign campaign,
            String name,
            MetricType type,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.campaign = campaign;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public void updateValue(BigDecimal newValue) {
        this.value = newValue;
        this.lastCalculated = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isTargetAchieved() {
        if (value == null || targetValue == null) return false;

        return switch (type) {
            case COUNT, CURRENCY, PERCENTAGE -> value.compareTo(targetValue) >= 0;
            case DURATION, COST -> value.compareTo(targetValue) <= 0;
            default -> false;
        };
    }

    public void markAsAutomated() {
        this.automated = true;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal calculatePerformanceRatio() {
        if (targetValue == null || targetValue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return value.divide(targetValue, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
