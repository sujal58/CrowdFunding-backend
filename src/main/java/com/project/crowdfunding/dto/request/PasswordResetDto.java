package com.project.crowdfunding.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDto {

    @NotNull(message = "Reset or forgot?")
    private String message;

    @NotNull(message = "Email is required!")
    private String email;

    private String oldPassword;

    @NotNull(message = "New password is required!")
    private String newPassword;
}
