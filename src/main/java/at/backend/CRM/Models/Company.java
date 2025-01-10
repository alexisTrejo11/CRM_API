package at.backend.CRM.Models;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

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
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "tax_number")
    private String taxNumber;

    @Column(name = "industry")
    private String industry;

    @Column(name = "website")
    private String website;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<Deal> deals = new HashSet<>();

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