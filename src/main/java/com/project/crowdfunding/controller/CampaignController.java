package com.project.crowdfunding.controller;

import com.project.crowdfunding.Services.CampaignService;
import com.project.crowdfunding.dto.request.CampaignRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    private final HttpServletRequest servletRequest;

    @PostMapping
    public ResponseEntity<ApiResponse> createCampaign(
            @Valid @ModelAttribute CampaignRequestDto request
//            @RequestParam(value="file", required = false) MultipartFile[] images
            ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Campaign created successfully!",
                        campaignService.createCampaign(request),
                        servletRequest.getRequestURI()
                )
        );

    }

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

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCampaigns() {
        return ResponseEntity.ok(ApiResponse.success(
                "Campaign fetched successfully!",
                campaignService.getAllCampaigns(),
                servletRequest.getRequestURI()));
    }

    @GetMapping("/total")
    public ResponseEntity<ApiResponse> getAllCampaignsOfUser() {
        return ResponseEntity.ok(ApiResponse.success(
                "Campaign fetched successfully!",
                campaignService.getAllCampaignsOfUser(),
                servletRequest.getRequestURI()));
    }



//    @PutMapping("/{id}")
//    public ResponseEntity<CampaignResponseDto> update(@PathVariable Long id, @RequestBody CampaignRequestDto request) {
//        return ResponseEntity.ok(campaignService.update(id, request));
//    }

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
