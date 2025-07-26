package com.project.crowdfunding.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 30, unique = true)
    private String phone;

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


    @Column(name = "document_url_front", length = 255)
    private String documentUrlFront;

    @Column(name = "document_url_back", length = 255)
    private String documentUrlBack;

    @Column(name = "selfie_url", length = 255)
    private String imageUrl;

    @Column(name = "face_match_score")
    private Double faceMatchScore;

    @Column(name = "reviewed_by", length = 100)
    private String reviewedBy;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();


}
