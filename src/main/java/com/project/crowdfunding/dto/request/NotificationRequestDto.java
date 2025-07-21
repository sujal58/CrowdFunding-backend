package com.project.crowdfunding.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class NotificationRequestDto {

    @NotNull(message = "Username ID is required.")
    private String username;

    @NotBlank(message = "Notification message cannot be empty.")
    private String message;

    @NotNull(message = "Type of notification must be mentioned!")
    private String notificationType;

}
