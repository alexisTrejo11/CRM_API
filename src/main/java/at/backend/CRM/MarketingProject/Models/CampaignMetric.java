package at.backend.CRM.MarketingProject.Models;

import at.backend.CRM.MarketingProject.Utils.Enums.MetricType;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaign_metrics")
@Data
public class CampaignMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private MarketingCampaign campaign;

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

    public void calculateMetricValue() {
        // In a real implementation, this would calculate the metric based on the formula and data source
        // For now, we'll just set the lastCalculated timestamp
        lastCalculated = LocalDateTime.now();
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


}
