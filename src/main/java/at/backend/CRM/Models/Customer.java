package at.backend.CRM.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "company")
    private String company;

    @Column(name = "industry")
    private String industry;

    @Column(name = "brand_voice")
    private String brandVoice;

    @Column(name = "brand_colors")
    private String brandColors;

    @Column(name = "target_market")
    private String targetMarket;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ElementCollection
    private List<String> competitorUrls;

    @Column(name = "social_media_handles")
    private String socialMediaHandles;


    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Opportunity> opportunities;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Interaction> interactions;


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

