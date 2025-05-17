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
public class DonationResponseDto {

    private Long id;
    private Long donorId;
    private Long campaignId;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private OffsetDateTime createdAt;
}
