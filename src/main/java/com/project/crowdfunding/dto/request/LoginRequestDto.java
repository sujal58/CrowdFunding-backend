package com.project.crowdfunding.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

//    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Email or Username is required.")
    private String email_username;

    @NotBlank(message = "Password cannot be empty.")
    private String password;

}
