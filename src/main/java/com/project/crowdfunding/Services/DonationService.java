package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Donation;
import com.project.crowdfunding.dto.request.DonationRequestDto;
import com.project.crowdfunding.dto.response.DonationResponseDto;

import java.util.List;

public interface DonationService {

    Donation createDonation(Long campaignId, DonationRequestDto dto);
    List<Donation> getDonationsByCampaignId(Long campaignId);
    List<DonationResponseDto> getDonationsByDonorId(Long donorId);
    List<DonationResponseDto> getDonationsByDonor();
    Donation getByDonationId(Long id);
    List<Donation> getAllDonation();
}
