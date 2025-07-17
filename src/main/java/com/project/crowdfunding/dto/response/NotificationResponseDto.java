package com.project.crowdfunding.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class NotificationResponseDto {
    private Long notificationId;
    private String message;
    private boolean read;
    private String notificationType;
    private String username;
    private boolean broadcast;
    private OffsetDateTime createdAt;
}

