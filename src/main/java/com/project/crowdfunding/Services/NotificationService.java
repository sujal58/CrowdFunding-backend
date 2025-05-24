package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Notification;
import com.project.crowdfunding.dto.request.NotificationRequestDto;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();
    Notification sendNotification(NotificationRequestDto dto);
    List<Notification> getNotificationsForUser(Long userId);
    List<Notification> getUnreadNotifications(Long userId);
    Notification getNotificationById(Long id);
    void deleteNotificationById(Long id);
}
