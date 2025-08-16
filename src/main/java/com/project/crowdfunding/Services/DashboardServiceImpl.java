package com.project.crowdfunding.Services;

import com.project.crowdfunding.Enums.CampaignStatus;
import com.project.crowdfunding.Enums.KycStatus;
import com.project.crowdfunding.Repository.CampaignRepository;
import com.project.crowdfunding.Repository.DonationRepository;
import com.project.crowdfunding.Repository.KycRepository;
import com.project.crowdfunding.Repository.UserRepository;
import com.project.crowdfunding.dto.response.AdminDashboardResponseDto;
import com.project.crowdfunding.dto.response.UserDashboardResponseDto;
import com.project.crowdfunding.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl {

    private final UserRepository userRepository;
    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    private final KycRepository kycRepository;
    private final AuthHelper authHelper;


    public UserDashboardResponseDto getUserData(){
        Long userId = authHelper.getAuthenticatedUser().getUserId();
        Long campaignCount = campaignRepository.countCampaignsByUserId(userId);
        Long activeCampaignCount = campaignRepository.countActiveCampaignsByUser(userId,CampaignStatus.ACTIVE);
        Double totalRaisedAmount = donationRepository.totalAmountCollectedFromUserCampaigns(userId);
        Long donationCount = donationRepository.countDonationsByUserId(userId);

        return new UserDashboardResponseDto(campaignCount, activeCampaignCount, totalRaisedAmount, donationCount);

    }

    public AdminDashboardResponseDto getAdminData(){

        Long campaignCount = campaignRepository.count();
        Double donationAmount = donationRepository.getTotalDoonationAmount();
        long totalUser = userRepository.count();
        Long pendingKyc = kycRepository.countTotalPendingKyc(KycStatus.PENDING);

        return new AdminDashboardResponseDto(campaignCount, donationAmount, totalUser, pendingKyc);

    }


}
