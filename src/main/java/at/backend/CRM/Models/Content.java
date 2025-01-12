package at.backend.CRM.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Column(nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContentStatus status;

    @Column(nullable = false)
    private LocalDateTime publishDate;

    @Column(nullable = false, length = 100)
    private String platform;

    @Column(nullable = false, length = 255)
    private String contentUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assigned_to", nullable = false)
    private User assignedTo;

    @Column(length = 255)
    private String approvalNotes;

    public enum ContentType {
        POST,
        STORY,
        REEL,
        AD,
        EMAIL
    }

    public enum ContentStatus {
        DRAFT,
        APPROVED,
        PUBLISHED
    }
}

