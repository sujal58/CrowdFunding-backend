package com.project.crowdfunding.dto.request;

import com.project.crowdfunding.Enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDto {

//    @NotNull(message = "Username ID is required.")
    private String username;

    @NotBlank(message = "Notification message cannot be empty.")
    private String message;

    @NotNull(message = "Type of notification must be mentioned!")
    private NotificationType notificationType;

}
