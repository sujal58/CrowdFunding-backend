package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Donation;

import java.util.List;

public interface DonationService {

    Donation createDonation(Donation donation);
    List<Donation> getDonationsByCampaignId(Long campaignId);
    List<Donation> getDonationsByDonorId(Long donorId);
}
