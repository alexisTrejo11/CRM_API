package at.backend.CRM.Models;

import at.backend.CRM.Utils.enums.DealStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "deals")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "opportunity_id", nullable = false)
    private Opportunity opportunity;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_status", nullable = false)
    private DealStatus dealStatus;

    @Column(name = "final_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal finalAmount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "campaign_manager_id")
    private User campaignManager;

    @Column(name = "deliverables", columnDefinition = "TEXT")
    private String deliverables;

    @Column(name = "terms", columnDefinition = "TEXT")
    private String terms;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "deal_service_packages",
            joinColumns = @JoinColumn(name = "deal_id"),
            inverseJoinColumns = @JoinColumn(name = "service_package_id"))
    private List<ServicePackage> services;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

