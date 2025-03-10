package at.backend.MarketingCompany.crm.interaction.domain;

import at.backend.MarketingCompany.crm.Utils.enums.FeedbackType;
import at.backend.MarketingCompany.crm.Utils.enums.InteractionType;
import at.backend.MarketingCompany.customer.api.repository.CustomerModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity
@Table(name = "interactions")
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CustomerModel customerModel;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InteractionType type;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "outcome", nullable = false)
    private String outcome;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "feedback_type")
    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @Column(name = "channel_preference")
    private String channelPreference;


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