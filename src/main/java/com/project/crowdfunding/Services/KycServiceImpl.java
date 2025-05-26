package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Kyc;
import com.project.crowdfunding.Entity.User;
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

        if(frontDoc.isEmpty() || backDoc.isEmpty() || image.isEmpty()){
            throw new IllegalArgumentException("Images are required!");
        }

        if (!(image.getContentType().startsWith("image/") && frontDoc.getContentType().startsWith("image/") && backDoc.getContentType().startsWith("image/"))) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String username = authHelper.getAuthenticatedUsername();
        User user = userService.getByUsername(username);
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
        return kycRepository.findByUserUserId(userId).orElseThrow(()-> new RuntimeException("No kyc found of user id: "+ userId));
    }

    @Transactional
    @Override
    public Kyc changeStatusByUserId(Long userId, String status) {
       User user = userService.getUserById(userId);
       String admin = authHelper.getAuthenticatedUsername();
       if(user.getKyc() == null){
           throw new RuntimeException("No kyc found of user: "+ user.getUsername());
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
            throw new RuntimeException("No kyc found of user: "+ user.getUsername());
        }
        Kyc kyc = user.getKyc();
        kycRepository.delete(kyc);
        user.setKyc(null);
        user.setKycStatus(KycStatus.PENDING);
        userService.saveUser(user);
    }

}

