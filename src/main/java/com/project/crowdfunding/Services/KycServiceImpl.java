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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final AuthHelper authHelper;

    @Override
    public KycResponseDto submitVerification(KycRequestDto kycDto) {
        String username = authHelper.getAuthenticatedUsername();
        User user = userService.getByUsername(username);

        Kyc kyc = modelMapper.map(kycDto, Kyc.class);
        kyc.setUser(user);

        Kyc savedKyc = kycRepository.save(kyc);
        return modelMapper.map(savedKyc, KycResponseDto.class);
    }

    @Override
    public List<Kyc> getAllKyc() {
        return kycRepository.findAll();
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
        userService.saveUser(user);
    }
}

