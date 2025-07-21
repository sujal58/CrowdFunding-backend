package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.Enums.CampaignStatus;
import com.project.crowdfunding.dto.request.CampaignRequestDto;
import com.project.crowdfunding.dto.response.CampaignResponseDto;

import java.util.List;

public interface CampaignService {

    List<Campaign> getAllCampaignsOfUser();
    CampaignResponseDto createCampaign(CampaignRequestDto campaign);
    CampaignResponseDto getCampaignById(Long id);
    List<CampaignResponseDto> getCampaignByStatus(CampaignStatus status);
    List<CampaignResponseDto> getCampaignsByUserId(Long userId);
    List<CampaignResponseDto> getAllCampaigns();
    void deleteCampaign(Long id);
    Campaign changeCampaignStatus(Long campaignId, String status);
}

//@RequestParam(value="file", required = false) MultipartFile[] files,
//            @RequestParam(value = "doc", required = false) MultipartFile doc