package com.project.crowdfunding.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentIntentRequest {

    @NotNull(message = "Donor email is required to proceed payment")
    private String email;

    @NotNull(message = "Campaign id is required to proceed payment")
     private Long campaignId;

    @NotNull(message = "Campaign name is required to proceed payment")
    private String campaignName;

    @NotNull(message = "Donation amount is required to proceed payment")
    private BigDecimal amount;
}
