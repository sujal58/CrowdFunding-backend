package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.AuthService.AuthService;
import com.project.crowdfunding.dto.request.LoginRequestDto;
import com.project.crowdfunding.dto.request.SignUpRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> login(@RequestBody SignUpRequestDto requestDto, HttpServletRequest request){
        return ResponseEntity.ok(ApiResponse.success("Registered successfully!", authService.registerUser(requestDto), request.getRequestURI()));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestDto requestDto, HttpServletRequest request){
        return ResponseEntity.ok(ApiResponse.success("Logged in successfully!", authService.login(requestDto), request.getRequestURI()));
    }
}
