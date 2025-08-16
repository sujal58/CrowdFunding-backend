package com.project.crowdfunding.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank(message = "Name is required.")
    private String name;

    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "username is required.")
    private String username;

    @NotBlank(message = "Country is required.")
    private String country;

    @NotBlank(message = "Password cannot be empty.")
    private String password;

}
