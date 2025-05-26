package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.KycService;
import com.project.crowdfunding.dto.request.KycRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;

    private final HttpServletRequest servletRequest;

    @PostMapping
    public ResponseEntity<ApiResponse> createKyc(
            @Valid @ModelAttribute KycRequestDto request,
            @RequestParam("frontDoc") MultipartFile frontDoc,
            @RequestParam("backDoc")MultipartFile backDoc,
            @RequestParam("image")MultipartFile image
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "KYC submitted successfully!",
                        kycService.submitVerification(request, frontDoc, backDoc, image),
                        servletRequest.getRequestURI()
                )
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getKycByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "KYC data fetched successfully!",
                        kycService.getByUserId(userId),
                        servletRequest.getRequestURI()
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllKyc() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All KYC records fetched successfully!",
                        kycService.getAllKyc(),
                        servletRequest.getRequestURI()
                )
        );
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteKyc(@PathVariable Long userId) {
        kycService.deleteKycByUserId(userId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "KYC deleted successfully!"
                )
        );
    }

    @PostMapping("/status")
    public ResponseEntity<ApiResponse> changeKycStatusByUserId(@RequestParam(value = "userId") Long userId, @RequestParam(value = "status") String status){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "KYC status updated successfully!",
                        kycService.changeStatusByUserId(userId, status),
                        servletRequest.getRequestURI()
                )
        );
    }



//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse> update(
//            @PathVariable Long id,
//            @RequestBody KycRequestDto request
//    ) {
//        return ResponseEntity.ok(
//                ApiResponse.success(
//                        "KYC updated successfully!",
//                        kycService.update(id, request),
//                        servletRequest.getRequestURI()
//                )
//        );
//    }
}
