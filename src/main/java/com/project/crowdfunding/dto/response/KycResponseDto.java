package com.project.crowdfunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycResponseDto {

    private Long kycId;
    private Long user;
    private String name;
    private String address;
    private String contactNo;
    private String documentNumber;
    private String documentUrlFront;
    private String documentUrlBack;
    private String imageUrl;
    private String status;
    private String reviewedBy;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
}
