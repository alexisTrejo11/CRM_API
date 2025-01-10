package at.backend.CRM.Models;

import java.time.LocalDateTime;

import at.backend.CRM.Models.enums.ActivityStatus;
import at.backend.CRM.Models.enums.ActivityType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;

    @Column(nullable = false)
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

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