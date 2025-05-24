package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Kyc;
import com.project.crowdfunding.dto.request.KycRequestDto;
import com.project.crowdfunding.dto.response.KycResponseDto;

import java.util.List;

public interface KycService {

    KycResponseDto submitVerification(KycRequestDto kyc);
    List<Kyc> getAllKyc();
    Kyc getByUserId(Long userId);
    Kyc changeStatusByUserId(Long userId, String status);
    void deleteKycByUserId(Long userId);
}
