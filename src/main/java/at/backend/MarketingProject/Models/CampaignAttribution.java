package at.backend.MarketingProject.Models;

import at.backend.CRM.Models.Deal;
import at.backend.MarketingProject.Models.Utils.AttributionModel;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaign_attributions")
@Data
public class CampaignAttribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private MarketingCampaign campaign;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttributionModel attributionModel;

    @Column(name = "attribution_percentage", precision = 5, scale = 2)
    private BigDecimal attributionPercentage;

    @Column(name = "attributed_revenue", precision = 19, scale = 2)
    private BigDecimal attributedRevenue;

    @Column(name = "first_touch_date")
    private LocalDateTime firstTouchDate;

    @Column(name = "last_touch_date")
    private LocalDateTime lastTouchDate;

    @Column(name = "touch_count")
    private Integer touchCount;

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
}