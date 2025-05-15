package com.project.crowdfunding.Entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.project.crowdfunding.Enums.KycStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kyc_verifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kyc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "document_type", length = 50)
    private String documentType;

    @Column(name = "document_number", length = 100)
    private String documentNumber;

    @Column(name = "name_on_document", length = 255)
    private String nameOnDocument;

    @Column(name = "address_on_document")
    private String addressOnDocument;

    @Column(name = "dob_on_document")
    private LocalDate dobOnDocument;

    @Column(name = "email_on_document", length = 255)
    private String emailOnDocument;

    @Column(name = "phone_on_document", length = 30)
    private String phoneOnDocument;

    @Column(name = "document_url", length = 255)
    private String documentUrl;

    @Column(name = "selfie_url", length = 255)
    private String selfieUrl;

    @Column(name = "face_match_score", precision = 5, scale = 2)
    private BigDecimal faceMatchScore;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private KycStatus status = KycStatus.PENDING;

    @Column(name = "reviewed_by", length = 100)
    private String reviewedBy;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();


}
