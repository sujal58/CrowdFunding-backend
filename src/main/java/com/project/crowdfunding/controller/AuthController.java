package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.AuthService.AuthService;
import com.project.crowdfunding.dto.request.LoginRequestDto;
import com.project.crowdfunding.dto.request.SignUpRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user login and registration")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "User Registration",
            description = "Allows a new user to sign up using their email, password, and additional required information."
    )
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> login(
            @Valid @RequestBody SignUpRequestDto requestDto,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Registered successfully!", authService.registerUser(requestDto), request.getRequestURI())
        );
    }

    @Operation(
            summary = "User Login",
            description = "Authenticates an existing user using their credentials and returns a JWT token upon success."
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Logged in successfully!", authService.login(requestDto), request.getRequestURI())
        );
    }
}
