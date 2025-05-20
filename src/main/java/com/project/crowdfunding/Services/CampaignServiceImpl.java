package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.Repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    @Override
    public Campaign createCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    @Override
    public Optional<Campaign> getCampaignById(Long id) {
        return campaignRepository.findById(id);
    }

    @Override
    public List<Campaign> getCampaignsByUserId(Long userId) {
        return campaignRepository.findByUserUserId(userId);
    }

    @Override
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }
}

