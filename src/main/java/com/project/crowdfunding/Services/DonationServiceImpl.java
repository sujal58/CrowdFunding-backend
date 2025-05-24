package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.Entity.Donation;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.TransactionStatus;
import com.project.crowdfunding.Repository.CampaignRepository;
import com.project.crowdfunding.Repository.DonationRepository;
import com.project.crowdfunding.dto.request.DonationRequestDto;
import com.project.crowdfunding.utils.AuthHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final AuthHelper authHelper;

    private final CampaignRepository campaignRepository;

    @Override
    @Transactional
    public Donation createDonation(Long campaignId, DonationRequestDto donationDto) {
        User user = authHelper.getAuthenticatedUser();
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(()-> new IllegalArgumentException("Campaign not exist with id: "+campaignId));


        Donation donation = modelMapper.map(donationDto, Donation.class);
        donation.setUser(user);
        donation.setCampaign(campaign);
        donation.setStatus(TransactionStatus.COMPLETED);
       campaign.setCurrentAmount(campaign.getCurrentAmount().add(donationDto.getAmount()));
        Donation savedDonation = donationRepository.save(donation);
        user.getDonations().add(savedDonation);
        userService.saveUser(user);
        return savedDonation;
    }

    @Override
    public List<Donation> getDonationsByCampaignId(Long campaignId) {
        return donationRepository.findByCampaignCampaignId(campaignId);
    }

    @Override
    public List<Donation> getDonationsByDonor(Long donorId) {
        User user = userService.getUserById(donorId);
        return donationRepository.findByUser(user);
    }

    @Override
    public List<Donation> getDonationsByDonor() {
        String username = authHelper.getAuthenticatedUsername();
        User user = userService.getByUsername(username);
        return donationRepository.findByUser(user);
    }

    @Override
    public Donation getByDonationId(Long id) {
        return donationRepository.findByDonationId(id).orElseThrow(()-> new RuntimeException("Donation not found with id: "+ id));
    }

    @Override
    public List<Donation> getAllDonation() {
       return donationRepository.findAll();
    }
}


