package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.CampaignStatus;
import com.project.crowdfunding.Enums.KycStatus;
import com.project.crowdfunding.Repository.CampaignRepository;
import com.project.crowdfunding.Repository.UserRepository;
import com.project.crowdfunding.dto.request.CampaignRequestDto;
import com.project.crowdfunding.dto.response.CampaignResponseDto;
import com.project.crowdfunding.utils.AuthHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    
    private final AuthHelper authHelper;

    private final FileService fileService;

    @Override
    public CampaignResponseDto createCampaign(
            @Valid CampaignRequestDto campaignDto
//            MultipartFile[] images

    ) {


        for(MultipartFile x: campaignDto.getFile()){
            System.out.println(x.getOriginalFilename());
        }

        String username = authHelper.getAuthenticatedUsername();

        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found!"));

        if(user.getKycStatus() != KycStatus.VERIFIED){
            throw new IllegalArgumentException("Verify your kyc to proceed");
        }

        // Map DTO to Entity
        Campaign campaign = new Campaign();
        campaign.setTitle(campaignDto.getTitle());
        campaign.setDescription(campaignDto.getDescription());
        campaign.setGoalAmount(campaignDto.getGoalAmount());
        campaign.setCurrentAmount(BigDecimal.ZERO);
        campaign.setStatus(CampaignStatus.PENDING);
        campaign.setUser(user);
        campaign.setCreatedAt(LocalDateTime.now());



        for(MultipartFile image: campaignDto.getFile()){
            String savedImages;
            if(!image.getContentType().startsWith("/image")){
                throw new IllegalArgumentException("Supporting document can only have image file!");
            }

            savedImages = fileService.uploadImage(image, "campaign");
            campaign.getImages().add(savedImages);
        }

        // Save campaign
        Campaign savedCampaign = campaignRepository.save(campaign);

        // Map Entity to Response DTO
        return modelMapper.map(savedCampaign, CampaignResponseDto.class);
    }

    @Override
    public CampaignResponseDto getCampaignById(Long id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return modelMapper.map(campaign, CampaignResponseDto.class);
    }

    @Override
    public List<CampaignResponseDto> getCampaignsByUserId(Long userId) {
        List<Campaign> campaigns = campaignRepository.findByUserUserId(userId);
        return campaigns.stream().map(campaign -> modelMapper.map(campaign, CampaignResponseDto.class)).toList();
    }

    @Override
    public List<CampaignResponseDto> getAllCampaigns() {
        List<Campaign> campaigns = campaignRepository.findAll();
        return campaigns.stream().map(campaign -> modelMapper.map(campaign, CampaignResponseDto.class)).toList();
    }


    @Override
    public List<Campaign> getAllCampaignsOfUser() {
        User user = authHelper.getAuthenticatedUser();
        return user.getCampaigns();
        }

    @Override
    public void deleteCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        campaignRepository.deleteById(id);
    }
}

