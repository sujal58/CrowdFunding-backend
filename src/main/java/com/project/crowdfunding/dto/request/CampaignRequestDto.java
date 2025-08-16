package com.project.crowdfunding.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CampaignRequestDto {

    @NotBlank(message = "Campaign title is required.")
    private String title;

    @NotBlank(message = "Campaign description is required.")
    private String description;

    @NotNull(message = "Goal amount is required.")
    @DecimalMin(value = "1.00", message = "Goal amount must be at least 1.00")
    private BigDecimal goalAmount;

    private List<String> tags;

    @NotNull(message = "Campaign image is required!")
    private MultipartFile campaignImage;

    private MultipartFile[] supportingImages;

    private Long userId;
}
