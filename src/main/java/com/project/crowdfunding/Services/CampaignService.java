package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.dto.request.CampaignRequestDto;
import com.project.crowdfunding.dto.response.CampaignResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CampaignService {

    CampaignResponseDto createCampaign(CampaignRequestDto campaign);
    CampaignResponseDto getCampaignById(Long id);
    List<CampaignResponseDto> getCampaignsByUserId(Long userId);
    List<CampaignResponseDto> getAllCampaigns();
    List<Campaign> getAllCampaignsOfUser();
    void deleteCampaign(Long id);
}

//@RequestParam(value="file", required = false) MultipartFile[] files,
//            @RequestParam(value = "doc", required = false) MultipartFile doc