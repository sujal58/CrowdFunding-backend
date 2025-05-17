package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Campaign;

import java.util.List;
import java.util.Optional;

public interface CampaignService {

    Campaign createCampaign(Campaign campaign);
    Optional<Campaign> getCampaignById(Long id);
    List<Campaign> getCampaignsByUserId(Long userId);
    List<Campaign> getAllCampaigns();
    void deleteCampaign(Long id);
}
