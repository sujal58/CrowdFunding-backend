package com.project.crowdfunding.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DonationRequestDto {


    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "1.00", message = "Donation amount must be at least 1.00")
    private BigDecimal amount;
}
