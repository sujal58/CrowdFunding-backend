package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.DonationServiceImpl;
import com.project.crowdfunding.dto.request.DonationRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationServiceImpl donationService;

    private final HttpServletRequest servletRequest;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllDonation() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Donations fetched successfully!",
                        donationService.getAllDonation(),
                        servletRequest.getRequestURI()
                )
        );
    }

    @PostMapping("/{campaignId}")
    public ResponseEntity<ApiResponse> createDonation(@PathVariable Long campaignId, @RequestBody DonationRequestDto requestDto) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Donation created successfully!",
                        donationService.createDonation(campaignId, requestDto),
                        servletRequest.getRequestURI()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDonationById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Donation fetched successfully!",
                        donationService.getByDonationId(id),
                        servletRequest.getRequestURI()
                )
        );
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<ApiResponse> getDonationByUserId(@PathVariable Long donorId){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Donations by donor fetched successfully!",
                        donationService.getDonationsByDonor(donorId),
                        servletRequest.getRequestURI()
                )
        );
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getDonationByUser(){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Donations for current user fetched successfully!",
                        donationService.getDonationsByDonor(),
                        servletRequest.getRequestURI()
                )
        );
    }



//    @PutMapping("/{id}")
//    public ResponseEntity<DonationResponseDTO> update(@PathVariable Long id, @RequestBody DonationRequestDTO request) {
//        return ResponseEntity.ok(donationService.update(id, request));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        donationService.(id);
//        return ResponseEntity.noContent().build();
//    }
}
