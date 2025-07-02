package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.KycService;
import com.project.crowdfunding.dto.request.KycRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/kyc")
@RequiredArgsConstructor
@Tag(name = "KYC", description = "Endpoints for submitting, retrieving, updating, and managing KYC verification")
public class KycController {

    private final KycService kycService;
    private final HttpServletRequest servletRequest;

    @Operation(
            summary = "Submit KYC Verification",
            description = "Allows a user to submit KYC details along with required documents and images for verification."
    )
    @PostMapping("/submit")
    public ResponseEntity<ApiResponse> createKyc(
            @Valid @ModelAttribute KycRequestDto request,
            @RequestParam("frontDoc") MultipartFile frontDoc,
            @RequestParam("backDoc") MultipartFile backDoc,
            @RequestParam("image") MultipartFile image
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "KYC submitted successfully!",
                        kycService.submitVerification(request, frontDoc, backDoc, image),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get KYC by User ID",
            description = "Fetches KYC details for a specific user using their user ID."
    )
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

    @Operation(
            summary = "Get All KYC Records",
            description = "Retrieves all submitted KYC records, typically for admin review."
    )
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

    @Operation(
            summary = "Delete KYC by User ID",
            description = "Deletes KYC information of a user by their ID."
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteKyc(@PathVariable Long userId) {
        kycService.deleteKycByUserId(userId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "KYC deleted successfully!"
                )
        );
    }

    @Operation(
            summary = "Update KYC Status",
            description = "Allows admin to update the KYC verification status (e.g., approved, rejected) for a specific user."
    )
    @PostMapping("/status")
    public ResponseEntity<ApiResponse> changeKycStatusByUserId(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "status") String status
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "KYC status updated successfully!",
                        kycService.changeStatusByUserId(userId, status),
                        servletRequest.getRequestURI()
                )
        );
    }

//    @Operation(
//        summary = "Update KYC Details",
//        description = "Updates KYC details for a specific KYC record by ID."
//    )
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
