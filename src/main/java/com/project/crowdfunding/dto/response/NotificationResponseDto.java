package com.project.crowdfunding.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class NotificationResponseDto {
    private Long notificationId;
    private String message;
    private String actionUrl;
    private String actionLabel;
    private boolean read;
    private String notificationType;
    private OffsetDateTime createdAt;
}

