package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Kyc;
import com.project.crowdfunding.dto.request.KycRequestDto;
import com.project.crowdfunding.dto.response.KycResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface KycService {

    KycResponseDto submitVerification(KycRequestDto kyc, MultipartFile frontDoc,
                                      MultipartFile backDoc,
                                      MultipartFile image);
    List<KycResponseDto> getAllKyc();
    KycResponseDto getByUserId(Long userId);
    Kyc changeStatusByUserId(Long userId, String status);
    void deleteKycByUserId(Long userId);
}
