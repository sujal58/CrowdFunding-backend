package com.project.crowdfunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycResponseDto {

    private String name;
    private String email;
    private String address;
    private String phone;
    private LocalDate dob;
    private String documentType;
    private String documentNumber;
    private String documentUrlFront;
    private String documentUrlBack;
    private String imageUrl;
    private String status;
    private Double faceMatchScore;
    private String reviewedBy;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
}
