package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.CampaignStatus;
import com.project.crowdfunding.Exception.KycNotVerifiedException;
import com.project.crowdfunding.Exception.ResourceNotFoundException;
import com.project.crowdfunding.Repository.CampaignRepository;
import com.project.crowdfunding.Repository.UserRepository;
import com.project.crowdfunding.dto.request.CampaignRequestDto;
import com.project.crowdfunding.dto.response.CampaignResponseDto;
import com.project.crowdfunding.utils.AuthHelper;
import com.project.crowdfunding.utils.ImageHelper;
import jakarta.transaction.Transactional;
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
public class CampaignServiceImpl implements CampaignService{

        private final CampaignRepository campaignRepository;

        private final UserRepository userRepository;

        private final ModelMapper modelMapper;

        private final AuthHelper authHelper;

        private final FileService fileService;

        @Override
        public CampaignResponseDto createCampaign(
                @Valid CampaignRequestDto campaignDto
        ) {
            String username = authHelper.getAuthenticatedUsername();
            User user = userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
            if(!user.isVerified()){
                throw new KycNotVerifiedException("User must complete KYC to access this resource.");
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

            if (campaignDto.getSupportingImages() != null) {
                for (MultipartFile image : campaignDto.getSupportingImages()) {
                    String savedImages;
                    if (image == null || image.isEmpty()) {
                        continue;
                    }
                    if (!image.getContentType().startsWith("image/")) {
                        throw new IllegalArgumentException("Supporting document can only have image file!");
                    }
                    savedImages = fileService.uploadImage(image, "campaign");
                    campaign.getSupportingImages().add(savedImages);
                }
            }
            if(campaignDto.getCampaignImage() == null){
                throw new IllegalArgumentException("Campaign Image cannot be empty!");
            }
            String savedCampaignImages = fileService.uploadImage(campaignDto.getCampaignImage(), "campaign");
            campaign.setCampaignImage(savedCampaignImages);
            // Save campaign
            Campaign savedCampaign = campaignRepository.save(campaign);
            // Map Entity to Response DTO
            return modelMapper.map(savedCampaign, CampaignResponseDto.class);
        }

        @Override
        public CampaignResponseDto getCampaignById(Long id) {
            Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Campaign not found with id: " + id));
            return modelMapper.map(campaign, CampaignResponseDto.class);
        }

        @Override
        public List<CampaignResponseDto> getCampaignByStatus(CampaignStatus status) {
        if(status == null){
            throw new IllegalArgumentException("Campaign status is required!");
        }

        List<Campaign> campaigns = campaignRepository.findByStatus(status);
            return campaigns.stream().map(campaign -> {
                CampaignResponseDto dto = modelMapper.map(campaign, CampaignResponseDto.class);
                dto.setCampaignImage(ImageHelper.buildImageUrl(campaign.getCampaignImage()));
                return dto;
            }).toList();    }

        @Override
        public List<CampaignResponseDto> getCampaignsByUserId(Long userId) {
            List<Campaign> campaigns = campaignRepository.findByUserUserId(userId);
            return campaigns.stream().map(campaign -> modelMapper.map(campaign, CampaignResponseDto.class)).toList();
        }

        @Override
        public List<CampaignResponseDto> getAllCampaigns() {
            List<Campaign> campaigns = campaignRepository.findAll();
            return campaigns.stream().map(campaign -> {
                CampaignResponseDto dto = modelMapper.map(campaign, CampaignResponseDto.class);
                dto.setCampaignImage(ImageHelper.buildImageUrl(campaign.getCampaignImage()));
                return dto;
            }).toList();
        }


        @Override
        public List<Campaign> getAllCampaignsOfUser() {
            User user = authHelper.getAuthenticatedUser();
            return user.getCampaigns();
        }

        @Override
        public void deleteCampaign(Long id) {
            campaignRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
            campaignRepository.deleteById(id);
        }

        @Transactional
        @Override
        public Campaign changeCampaignStatus(Long campaignId, String status) {
            Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(() -> new IllegalArgumentException("Campaign not found with id: " + campaignId));
            campaign.setStatus(CampaignStatus.fromString(status));
            return campaignRepository.save(campaign);
        }
    }

