package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Donation;
import com.project.crowdfunding.dto.request.DonationRequestDto;

import java.util.List;

public interface DonationService {

    Donation createDonation(Long campaignId, DonationRequestDto dto);
    List<Donation> getDonationsByCampaignId(Long campaignId);
    List<Donation> getDonationsByDonor(Long donorId);
    List<Donation> getDonationsByDonor();
    Donation getByDonationId(Long id);
    List<Donation> getAllDonation();
}
