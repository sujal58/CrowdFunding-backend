package com.project.crowdfunding.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crowdfunding.Entity.Campaign;
import com.project.crowdfunding.Entity.Kyc;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.CampaignStatus;
import com.project.crowdfunding.Enums.KycStatus;
import com.project.crowdfunding.Repository.KycRepository;
import com.project.crowdfunding.dto.request.KycRequestDto;
import com.project.crowdfunding.dto.response.KycApiResponse;
import com.project.crowdfunding.dto.response.KycResponseDto;
import com.project.crowdfunding.utils.AuthHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

        if (kycRepository.existsByPhone(kycDto.getPhone())) {
            throw new IllegalArgumentException("KYC with this phone number already exists.");
        }

        if (kycRepository.existsByEmail(kycDto.getEmail())) {
            throw new IllegalArgumentException("KYC with this email address already exists.");
        }

        if (kycRepository.existsByDocumentNumber(kycDto.getDocumentNumber())) {
            throw new IllegalArgumentException("KYC with this document number already exists.");
        }

        if(frontDoc.isEmpty() || backDoc.isEmpty() || image.isEmpty()){
            throw new IllegalArgumentException("Images are required!");
        }

        if (!(image.getContentType().startsWith("image/") && frontDoc.getContentType().startsWith("image/") && backDoc.getContentType().startsWith("image/"))) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        Kyc kyc = modelMapper.map(kycDto, Kyc.class);
        kyc.setUser(user);

        // Get base path for uploads (configured in application.properties)
        String uploadBasePath = "D:/crowdfunding/crowdfunding/";

        String frontDocPath = fileService.uploadImage(frontDoc, "docFront");
        String backDocPath = fileService.uploadImage(backDoc, "docBack");
        String imagePath = fileService.uploadImage(image, "image");

        // Convert relative paths to absolute paths
        String normalizedFrontDocPath = Paths.get(uploadBasePath, frontDocPath).toAbsolutePath().normalize().toString();
        String normalizedBackDocPath = Paths.get(uploadBasePath, backDocPath).toAbsolutePath().normalize().toString();
        String normalizedImagePath = Paths.get(uploadBasePath, imagePath).toAbsolutePath().normalize().toString();
        System.out.println(normalizedImagePath);

        // Verify file existence
        for (String path : List.of(normalizedFrontDocPath, normalizedBackDocPath, normalizedImagePath)) {
            if (!new java.io.File(path).exists()) {
                throw new IllegalArgumentException("File not found: " + path);
            }
        }
        kyc.setDocumentUrlFront(frontDocPath);
        kyc.setDocumentUrlBack(backDocPath);
        kyc.setImageUrl(imagePath);

        // Prepare request to FastAPI
        String fastApiUrl = "http://127.0.0.1:8000/api/v1/kyc-verify";
        ObjectMapper objectMapper = new ObjectMapper();

        try(HttpClient client = HttpClient.newHttpClient()) {
            // Construct JSON payload
            Map<String, String> requestBody = Map.of(
                    "document_front_path", normalizedFrontDocPath,
                    "document_back_path", normalizedBackDocPath,
                    "user_image_path", normalizedImagePath,
                    "name", kycDto.getName(),
                    "document_number", kycDto.getDocumentNumber()
            );

            // Convert to JSON string
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fastApiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            KycApiResponse kycResponse = objectMapper.readValue(response.body(), KycApiResponse.class);

            // Process FastAPI response
            String status = kycResponse.getStatus();
            int code = kycResponse.getCode();
            double match_score = kycResponse.getKyc_score();
            double ocr_confidence = kycResponse.getConfidence();
            kyc.setFaceMatchScore(match_score);
            kyc.setReviewedBy("Auto Kyc Algorithm");

            if ("success".equals(status)) {
                kyc.getUser().setKycStatus(KycStatus.VERIFIED); // Adjust enum name if different
            } else {
                kyc.getUser().setKycStatus(KycStatus.REJECTED); // Adjust enum name if different
            }
        } catch (Exception e) {
            // Handle FastAPI errors (e.g., 400 for invalid paths, 500 for server errors)
            kyc.getUser().setKycStatus(KycStatus.PENDING);
            throw new RuntimeException("KYC verification failed: " + e.getMessage());
        }

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
    public KycResponseDto getByUserId(Long userId) {
        Kyc kyc = kycRepository.findByUserUserId(userId).orElseThrow(()-> new IllegalArgumentException("No kyc found of user id: "+ userId));
        return modelMapper.map(kyc, KycResponseDto.class);
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

