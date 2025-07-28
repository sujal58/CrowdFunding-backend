package com.project.crowdfunding.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDashboardResponseDto {

    private Long campaignCount;
    private Long activeCampaignCount;
    private Double totalDonationRaised;
    private Long donationCount;
}
