package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Kyc;
import com.project.crowdfunding.Repository.KycRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;

    @Override
    public Kyc submitVerification(Kyc kyc) {
        return kycRepository.save(kyc);
    }

    @Override
    public Optional<Kyc> getByUserId(Long userId) {
        return kycRepository.findByUserUserId(userId);
    }
}

