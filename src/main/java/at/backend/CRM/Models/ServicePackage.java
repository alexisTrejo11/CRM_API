package at.backend.CRM.Models;

import at.backend.CRM.Utils.enums.Complexity;
import at.backend.CRM.Utils.enums.Frequency;
import at.backend.CRM.Utils.enums.SocialNetworkPlatform;
import at.backend.CRM.Utils.enums.ServiceType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "services_packages")
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 50)
    private ServiceType serviceType;

    @Column(name = "deliverables", columnDefinition = "TEXT")
    private String deliverables;

    @Column(name = "estimated_hours", nullable = false)
    private Integer estimatedHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "complexity", nullable = false, length = 20)
    private Complexity complexity;

    @Column(name = "is_recurring", nullable = false)
    private Boolean isRecurring;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", length = 50)
    private Frequency frequency;

    @Column(name = "project_duration")
    private Integer projectDuration;

    @ElementCollection
    @CollectionTable(name = "marketing_service_kpis", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "kpi", nullable = false)
    private List<String> kpis;

    @ElementCollection
    @CollectionTable(name = "marketing_service_platforms", joinColumns = @JoinColumn(name = "service_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    private List<SocialNetworkPlatform> socialNetworkPlatforms;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
