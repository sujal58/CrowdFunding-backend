package com.project.crowdfunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycResponseDto {

    private Long id;
    private Long userId;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String documentNumber;
    private String documentUrl;
    private String selfieUrl;
    private String status;
    private BigDecimal confidenceScore;
    private OffsetDateTime createdAt;
}
