package com.project.crowdfunding.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVerificationResponse {
    private boolean success;
    private Long donationId;
    private String message;
}
