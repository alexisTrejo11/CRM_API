package at.backend.MarketingCompany.marketing.metric.api.repository;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MetricType;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "campaign_metrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignMetricModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private MarketingCampaignModel campaign;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetricType type;

    @Column(precision = 19, scale = 4)
    private BigDecimal value;

    @Column(name = "target_value", precision = 19, scale = 4)
    private BigDecimal targetValue;

    @Column(name = "measurement_unit")
    private String measurementUnit;

    @Column(name = "last_calculated")
    private LocalDateTime lastCalculated;

    @Column(name = "calculation_formula", length = 1000)
    private String calculationFormula;

    @Setter
    @Column(name = "revenue", precision = 19, scale = 4)
    private BigDecimal revenue;

    @Column(name = "data_source", length = 500)
    private String dataSource;

    @Column(name = "is_automated")
    private boolean automated = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }


    public boolean isTargetAchieved() {
        if (value == null || targetValue == null) return false;

        if (type == MetricType.COUNT || type == MetricType.CURRENCY || type == MetricType.PERCENTAGE) {
            return value.compareTo(targetValue) >= 0;
        } else if (type == MetricType.DURATION || type == MetricType.COST) {
            // Lower is better for duration and cost
            return value.compareTo(targetValue) <= 0;
        }

        return false;
    }

    public BigDecimal getRevenue() {
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
}