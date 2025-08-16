
package com.project.crowdfunding.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponseDto {

    private Long campaignCount;
    private Double donationAmount;
    private long totalUser;
    private Long pendingKyc;
}