package com.project.crowdfunding.controller;

import com.project.crowdfunding.Services.EmailService;
import com.project.crowdfunding.Services.UserService;
import com.project.crowdfunding.dto.response.ApiResponse;
import com.project.crowdfunding.utils.OtpHelper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/otp")
@RequiredArgsConstructor
@Slf4j
public class OtpController {

    private final UserService userService;
    private final EmailService emailService;

    @Operation(
            summary = "Generate and send otp to email",
            description = "Generate a random 6 digit otp , store it in cache and send to provided email"
    )
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse> generateAndSendOTP(@RequestParam String email) throws MessagingException {
            boolean userExist = userService.userExistByEmail(email);
            System.out.println(userExist);
            if(!userExist){
                throw new IllegalArgumentException("User not found");
            }
            String otp = OtpHelper.generateOTP();
            OtpHelper.storeOtp(email, otp);
            log.info("otp: {}", otp);
            emailService.sendOTPEmail(email, otp);
            return ResponseEntity.ok(ApiResponse.success("OTP sent to " + email)) ;

    }

    @Operation(
            summary = "Verify entered otp",
            description = "Verify the entered otp with the otp stored in cached for respective email"
    )
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verifyOTP(@RequestParam String email, @RequestParam String otp) {
        if (OtpHelper.verifyOtp(email, otp)) {
            OtpHelper.removeOtp(email);
            return ResponseEntity.ok(ApiResponse.success("OTP verified successfully")) ;
        }
        throw new IllegalArgumentException("Invalid or expired OTP");

    }
}
