package com.project.crowdfunding.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="order_id", nullable = true)
    private String orderId;// Changed to paymentIntentId for Stripe

    @Column(name = "payment_id")
    private String paymentId; // Stripe paymentIntentId (e.g., pi_xxx)

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "campaign_id", nullable = false)
    private Long campaignId;

    @Column(name = "donation_id")
    private Long donationId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String status; // e.g., created, succeeded, failed

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}