package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.DonationServiceImpl;
import com.project.crowdfunding.dto.request.DonationRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/donations")
@RequiredArgsConstructor
@Tag(name = "Donations", description = "Endpoints for creating, fetching, updating, and deleting donation records")
public class DonationController {

    private final DonationServiceImpl donationService;
    private final HttpServletRequest servletRequest;

    @Operation(
            summary = "Get All Donations",
            description = "Fetches all donation records across all campaigns. Useful for administrative reporting."
    )
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

    @Operation(
            summary = "Create Donation",
            description = "Creates a donation for a specific campaign identified by campaignId."
    )
    @PostMapping("/{campaignId}")
    public ResponseEntity<ApiResponse> createDonation(
            @PathVariable Long campaignId,
            @Valid @RequestBody DonationRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Donation created successfully!",
                        donationService.createDonation(campaignId, requestDto),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get Donation by ID",
            description = "Retrieves a donation record by its unique donation ID."
    )
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

    @Operation(
            summary = "Get Donations by Donor ID",
            description = "Fetches all donations made by a specific donor identified by donorId."
    )
    @GetMapping("/donor/{donorId}")
    public ResponseEntity<ApiResponse> getDonationByUserId(@PathVariable Long donorId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Donations by donor fetched successfully!",
                        donationService.getDonationsByDonor(donorId),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get Donations for Current User",
            description = "Fetches all donations made by the currently authenticated user."
    )
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getDonationByUser() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Donations for current user fetched successfully!",
                        donationService.getDonationsByDonor(),
                        servletRequest.getRequestURI()
                )
        );
    }

//    @Operation(
//            summary = "Update Donation",
//            description = "Updates details of an existing donation record by donation ID."
//    )
//    @PutMapping("/{id}")
//    public ResponseEntity<DonationResponseDTO> update(
//            @PathVariable Long id,
//            @RequestBody DonationRequestDTO request
//    ) {
//        return ResponseEntity.ok(donationService.update(id, request));
//    }
//
//    @Operation(
//            summary = "Delete Donation",
//            description = "Deletes a donation record by its ID."
//    )
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        donationService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
