package at.backend.CRM.MarketingProject.Models;

import at.backend.CRM.CRM.Models.Deal;
import at.backend.CRM.MarketingProject.Models.Utils.CampaignStatus;
import at.backend.CRM.MarketingProject.Models.Utils.CampaignType;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "marketing_campaigns")
@Data
public class MarketingCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "budget", precision = 19, scale = 2)
    private BigDecimal budget;

    @Column(name = "cost_to_date", precision = 19, scale = 2)
    private BigDecimal costToDate = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignType type;

    @Column(name = "target_audience", length = 500)
    private String targetAudience;

    @Column(name = "success_criteria", length = 500)
    private String successCriteria;

    @ElementCollection
    @CollectionTable(
            name = "campaign_targets",
            joinColumns = @JoinColumn(name = "campaign_id")
    )
    @MapKeyColumn(name = "metric_name")
    @Column(name = "target_value")
    private Map<String, Double> targets = new HashMap<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignInteraction> interactions = new ArrayList<>();

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignMetric> metrics = new ArrayList<>();

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignActivity> activities = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "campaign_deals",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "deal_id")
    )
    private List<Deal> relatedDeals = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "campaign_segments",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id")
    )
    private List<CustomerSegment> targetSegments = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = CampaignStatus.DRAFT;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void updateMetrics() {
        for (CampaignMetric metric : metrics) {
            metric.calculateMetricValue();
        }
    }

    public boolean isActive() {
        return status == CampaignStatus.ACTIVE;
    }

    public boolean isCompleted() {
        return status == CampaignStatus.COMPLETED;
    }
}