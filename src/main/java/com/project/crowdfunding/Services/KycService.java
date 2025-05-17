package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Kyc;

import java.util.Optional;

public interface KycService {

    Kyc submitVerification(Kyc Vkyc);
    Optional<Kyc> getByUserId(Long userId);
}
