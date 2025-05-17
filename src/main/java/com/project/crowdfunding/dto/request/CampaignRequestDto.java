package com.project.crowdfunding.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CampaignRequestDto {

    @NotBlank(message = "Campaign title is required.")
    private String title;

    private String description;

    @NotNull(message = "Goal amount is required.")
    @DecimalMin(value = "1.00", message = "Goal amount must be at least 1.00")
    private BigDecimal goalAmount;

    @NotNull(message = "User ID is required.")
    private Long userId;
}
