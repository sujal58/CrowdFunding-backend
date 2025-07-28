package com.project.crowdfunding.controller;

import com.project.crowdfunding.Services.DashboardServiceImpl;
import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardServiceImpl dashboardService;

    public DashboardController(DashboardServiceImpl dashboardService){
        this.dashboardService = dashboardService;
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUserDashboardData(HttpServletRequest request){
        return ResponseEntity.ok(ApiResponse.success("Dashboard data fetched successfully!", dashboardService.getUserData(),request.getRequestURI()));
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse> getAdminDashboardData(HttpServletRequest request){
        return ResponseEntity.ok(ApiResponse.success("Dashboard data fetched successfully!", dashboardService.getAdminData(),request.getRequestURI()));
    }
}
