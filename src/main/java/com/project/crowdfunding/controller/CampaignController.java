package com.project.crowdfunding.controller;

import com.project.crowdfunding.Services.CampaignService;
import com.project.crowdfunding.dto.request.CampaignRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
@Tag(name = "Campaigns", description = "Endpoints for creating, viewing, and deleting crowdfunding campaigns")
public class CampaignController {

    private final CampaignService campaignService;
    private final HttpServletRequest servletRequest;

    @Operation(
            summary = "Create Campaign",
            description = "Creates a new fundraising campaign. The user must be KYC-verified. Accepts form data for fields like title, description, amount, deadline, etc."
    )
    @PostMapping
    public ResponseEntity<ApiResponse> createCampaign(
            @Valid @ModelAttribute CampaignRequestDto request
            // @RequestParam(value="file", required = false) MultipartFile[] images
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Campaign created successfully!",
                        campaignService.createCampaign(request),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get Campaign by ID",
            description = "Retrieves a specific campaign using its unique ID."
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse> getCampaignById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Campaign fetched successfully!",
                        campaignService.getCampaignById(id),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get Campaign by status",
            description = "Retrieves a specific campaign using its status."
    )
    @GetMapping("/campaign/{status}")
    public ResponseEntity<ApiResponse> getCampaignByStatus(@RequestParam String status) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        status + " campaign fetched successfully!",
                        campaignService.getCampaignByStatus(status),
                        servletRequest.getRequestURI()
                )
        );
    }

    @Operation(
            summary = "Get All Campaigns",
            description = "Retrieves a list of all available campaigns, regardless of the creator."
    )
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCampaigns() {
        return ResponseEntity.ok(ApiResponse.success(
                "Campaign fetched successfully!",
                campaignService.getAllCampaigns(),
                servletRequest.getRequestURI()));
    }

    @Operation(
            summary = "Get User's Campaigns",
            description = "Fetches all campaigns created by the currently authenticated user."
    )
    @GetMapping("/total")
    public ResponseEntity<ApiResponse> getAllCampaignsOfUser() {
        return ResponseEntity.ok(ApiResponse.success(
                "Campaign fetched successfully!",
                campaignService.getAllCampaignsOfUser(),
                servletRequest.getRequestURI()));
    }

    @Operation(
            summary = "Delete Campaign",
            description = "Deletes a campaign by its ID. Only the owner or an admin can perform this action."
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Campaign deleted successfully!"
                )
        );
    }
}
