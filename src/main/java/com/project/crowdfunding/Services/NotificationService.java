package com.project.crowdfunding.Services;

import com.project.crowdfunding.dto.request.NotificationRequestDto;
import com.project.crowdfunding.dto.response.NotificationResponseDto;

import java.util.List;

public interface NotificationService {
    List<NotificationResponseDto> getAllNotifications();
    NotificationResponseDto sendNotification(NotificationRequestDto dto);
    void broadcastNotification(NotificationRequestDto dto);
    List<NotificationResponseDto> getNotificationsForUser(Long userId);
    List<NotificationResponseDto> getUnreadNotifications(Long userId);
    NotificationResponseDto getNotificationById(Long id);
    void deleteNotificationById(Long id);
    NotificationResponseDto changeStatusById(Long notificationId, boolean isRead);

}
