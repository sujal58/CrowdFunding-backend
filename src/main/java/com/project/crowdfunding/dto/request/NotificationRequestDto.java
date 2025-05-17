package com.project.crowdfunding.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class NotificationRequestDto {

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotBlank(message = "Notification message cannot be empty.")
    private String message;
}
