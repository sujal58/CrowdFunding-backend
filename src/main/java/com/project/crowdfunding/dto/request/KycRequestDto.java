package com.project.crowdfunding.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class KycRequestDto {

    @NotBlank(message = "Full name is required.")
    private String name;

    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number.")
    private String contactNo;

    @NotBlank(message = "Document number is required.")
    private String documentNumber;

    @NotBlank(message = "Type of document is required.")
    private String documentType;

    @NotBlank(message = "DOB similar to document is required.")
    private LocalDate dob;


}
