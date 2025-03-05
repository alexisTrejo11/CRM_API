    package at.backend.MarketingCompany.marketing.customer;

    import at.backend.MarketingCompany.customer.api.repository.CustomerModel;
    import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
    import jakarta.persistence.*;
    import lombok.Data;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @Entity
    @Table(name = "customer_segments")
    @Data
    public class CustomerSegment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(length = 1000)
        private String description;

        @Column(name = "segment_criteria", length = 2000)
        private String segmentCriteria;

        @Column(name = "is_dynamic")
        private boolean dynamic = true;

        @ElementCollection
        @CollectionTable(
                name = "segment_rules",
                joinColumns = @JoinColumn(name = "segment_id")
        )
        @MapKeyColumn(name = "rule_field")
        @Column(name = "rule_value")
        private Map<String, String> rules = new HashMap<>();

        @ManyToMany(mappedBy = "targetSegments")
        private List<MarketingCampaignModel> campaigns = new ArrayList<>();

        @ManyToMany
        @JoinTable(
                name = "segment_customers",
                joinColumns = @JoinColumn(name = "segment_id"),
                inverseJoinColumns = @JoinColumn(name = "customer_id")
        )
        private List<CustomerModel> customerModels = new ArrayList<>();

        @Column(name = "last_updated")
        private LocalDateTime lastUpdated;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @PrePersist
        public void prePersist() {
            createdAt = LocalDateTime.now();
            updatedAt = LocalDateTime.now();
            lastUpdated = LocalDateTime.now();
        }

        @PreUpdate
        public void preUpdate() {
            updatedAt = LocalDateTime.now();
        }
    }
