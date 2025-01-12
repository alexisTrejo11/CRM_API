package at.backend.CRM.Models;


import at.backend.CRM.Models.enums.MetricType;
import at.backend.CRM.Models.enums.SocialNetworkPlatform;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "campaign_metrics")
public class CampaignMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MetricType metricType;

    @Column(nullable = false)
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SocialNetworkPlatform source;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double cost;

    @Column(length = 255)
    private String notes;
}

