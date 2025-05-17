package com.project.crowdfunding.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class KycRequestDto {

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotBlank(message = "Full name is required.")
    private String fullName;

    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number.")
    private String phoneNumber;

    @NotBlank(message = "Document number is required.")
    private String documentNumber;

    @NotBlank(message = "Document URL is required.")
    private String documentUrl;

    @NotBlank(message = "Selfie URL is required.")
    private String selfieUrl;
}
