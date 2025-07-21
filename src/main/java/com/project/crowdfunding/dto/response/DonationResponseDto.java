package com.project.crowdfunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationResponseDto {
    private String campaignName;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private LocalDateTime createdAt;
}
