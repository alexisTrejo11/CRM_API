package at.backend.CRM.Models;

import at.backend.CRM.Models.enums.CampaignPlatform;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CampaignObjective objective;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CampaignStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal budget;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignMetric> metrics;

    @ElementCollection
    @CollectionTable(name = "campaign_target_audience", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "audience", nullable = false)
    private List<String> targetAudience;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CampaignPlatform platforms;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Content> contents;
}
