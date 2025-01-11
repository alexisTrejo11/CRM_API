package at.backend.CRM.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import at.backend.CRM.Models.enums.DealStage;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deals")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "stage", nullable = false)
    @Enumerated(EnumType.STRING)
    private DealStage stage;

    @Column(name = "expected_close_date")
    private LocalDateTime expectedCloseDate;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "deal_contacts",
            joinColumns = @JoinColumn(name = "deal_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "deal", cascade = CascadeType.ALL)
    private Set<Activity> activities = new HashSet<>();

    @Column(name = "created_at")
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
