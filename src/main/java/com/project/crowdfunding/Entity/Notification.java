package com.project.crowdfunding.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import com.project.crowdfunding.Enums.NotificationType;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(nullable = false)
    private boolean read = false;

    @Column(name = "action_url", length = 255)
    private String actionUrl;

    @Column(name = "action_label", length = 50)
    private String actionLabel;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", length = 20)
    private NotificationType notificationType;

}
