package at.backend.CRM.MarketingProject.Models;

import at.backend.CRM.CRM.Models.Customer;
import at.backend.CRM.CRM.Models.Deal;
import at.backend.CRM.CRM.Utils.enums.MarketingInteractionType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "campaign_interactions")
@Data
public class CampaignInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private MarketingCampaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MarketingInteractionType marketingInteractionType;

    @Column(name = "interaction_date", nullable = false)
    private LocalDateTime interactionDate;

    @Column(name = "source_channel")
    private String sourceChannel;

    @Column(name = "source_medium")
    private String sourceMedium;

    @Column(name = "source_campaign")
    private String sourceCampaign;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "geo_location")
    private String geoLocation;

    @ElementCollection
    @CollectionTable(
            name = "interaction_properties",
            joinColumns = @JoinColumn(name = "interaction_id")
    )
    @MapKeyColumn(name = "property_name")
    @Column(name = "property_value")
    private Map<String, String> properties = new HashMap<>();

    @Column(length = 1000)
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resulted_deal_id")
    private Deal resultedDeal;

    @Column(name = "conversion_value")
    private Double conversionValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (interactionDate == null) {
            interactionDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}