package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.Entity.Kyc;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.CampaignStatus;
import com.project.crowdfunding.Enums.KycStatus;
import com.project.crowdfunding.Repository.KycRepository;
import com.project.crowdfunding.dto.request.KycRequestDto;
import com.project.crowdfunding.dto.response.KycResponseDto;
import com.project.crowdfunding.utils.AuthHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final AuthHelper authHelper;

    private final FileService fileService;

    @Override
    @Transactional
    public KycResponseDto submitVerification(
            KycRequestDto kycDto,
            MultipartFile frontDoc,
            MultipartFile backDoc,
            MultipartFile image
    ) {

        User user = authHelper.getAuthenticatedUser();

        Optional<Kyc> Optionalkyc = kycRepository.findByUserUserId(user.getUserId());

        if(Optionalkyc.isPresent()){
            throw new IllegalArgumentException("Kyc already exist! Please update if you want any change!");
        }

        if(frontDoc.isEmpty() || backDoc.isEmpty() || image.isEmpty()){
            throw new IllegalArgumentException("Images are required!");
        }

        if (!(image.getContentType().startsWith("image/") && frontDoc.getContentType().startsWith("image/") && backDoc.getContentType().startsWith("image/"))) {
            throw new IllegalArgumentException("Only image files are allowed");
        }


        String frontDocPath = fileService.uploadImage(frontDoc, "docFront");
        String backDocPath = fileService.uploadImage(backDoc, "docBack");
        String imagePath = fileService.uploadImage(image, "image");

        Kyc kyc = modelMapper.map(kycDto, Kyc.class);
        kyc.setUser(user);
        kyc.setDocumentUrlFront(frontDocPath);
        kyc.setDocumentUrlBack(backDocPath);
        kyc.setImageUrl(imagePath);

        Kyc savedKyc = kycRepository.save(kyc);
        KycResponseDto mappedKyc = modelMapper.map(savedKyc, KycResponseDto.class);
        mappedKyc.setStatus(user.getKycStatus().toString());
        return  mappedKyc;
    }

    @Override
    public List<KycResponseDto> getAllKyc() {
        return kycRepository.findAll().stream().map((kyc)->{

                    KycResponseDto mappedKyc = modelMapper.map(kyc, KycResponseDto.class);
                    mappedKyc.setStatus(kyc.getUser().getKycStatus().toString());
                    return mappedKyc;
                }
        ).toList();
    }

    @Override
    public Kyc getByUserId(Long userId) {
        return kycRepository.findByUserUserId(userId).orElseThrow(()-> new IllegalArgumentException("No kyc found of user id: "+ userId));
    }

    @Transactional
    @Override
    public Kyc changeStatusByUserId(Long userId, String status) {
       User user = userService.getUserById(userId);
       String admin = authHelper.getAuthenticatedUsername();
       if(user.getKyc() == null){
           throw new IllegalArgumentException("No kyc found of user: "+ user.getUsername());
       }
       user.setKycStatus(KycStatus.fromString(status));
       user.getKyc().setReviewedBy(admin);
       user.getKyc().setReviewedAt(LocalDateTime.now());
       User savedUser = userService.saveUser(user);
       return savedUser.getKyc();
    }

    @Transactional
    @Override
    public void deleteKycByUserId(Long userId) {
        User user = userService.getUserById(userId);
        if(user.getKyc() == null){
            throw new IllegalArgumentException("No kyc found of user: "+ user.getUsername());
        }
        Kyc kyc = user.getKyc();
        kycRepository.delete(kyc);

        fileService.deleteImage(kyc.getImageUrl());
        fileService.deleteImage(kyc.getDocumentUrlBack());
        fileService.deleteImage(kyc.getDocumentUrlFront());

        user.setKyc(null);
        user.setKycStatus(KycStatus.PENDING);
        for(Campaign campaign: user.getCampaigns()){
            if(campaign.getStatus().equals(CampaignStatus.ACTIVE)){
                campaign.setStatus(CampaignStatus.PENDING);
            }
        }
        userService.saveUser(user);
    }

}

