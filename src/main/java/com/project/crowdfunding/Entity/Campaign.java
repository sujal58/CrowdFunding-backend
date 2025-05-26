package com.project.crowdfunding.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.crowdfunding.Enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "campaigns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Long campaignId;

    // Relationship to User
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "goal_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal goalAmount;

    @Column(name = "current_amount", precision = 10, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CampaignStatus status = CampaignStatus.ACTIVE;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();


    private List<String> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "campaign_categories",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "campaign_tags",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}

