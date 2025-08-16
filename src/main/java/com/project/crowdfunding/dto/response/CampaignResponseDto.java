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
public class CampaignResponseDto {

    private Long id;
    private String title;
    private String description;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private String status;
    private LocalDateTime createdAt;
    private Long userId;
    private String username;
    private String campaignImage;
}
