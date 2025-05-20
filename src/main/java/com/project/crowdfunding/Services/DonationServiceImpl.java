package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Donation;
import com.project.crowdfunding.Repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    @Override
    public Donation createDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public List<Donation> getDonationsByCampaignId(Long campaignId) {
        return donationRepository.findByCampaignCampaignId(campaignId);
    }

    @Override
    public List<Donation> getDonationsByDonorId(Long donorId) {
        return donationRepository.findByDonationId(donorId);
    }
}


