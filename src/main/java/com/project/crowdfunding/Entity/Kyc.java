package com.project.crowdfunding.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "kyc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kyc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kyc_id")
    private Long kycId;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_no", length = 30, unique = true)
    private String contactNo;

    @Column(name = "document_number", length = 100, unique = true)
    private String documentNumber;

    @Column(name = "document_type", length = 50)
    private String documentType;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    //in dto

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonManagedReference
    private User user;


    @Column(name = "document_url", length = 255)
    private String documentUrl;

    @Column(name = "selfie_url", length = 255)
    private String selfieUrl;

    @Column(name = "face_match_score", precision = 5, scale = 2)
    private BigDecimal faceMatchScore;


    @Column(name = "reviewed_by", length = 100)
    private String reviewedBy;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();


}
