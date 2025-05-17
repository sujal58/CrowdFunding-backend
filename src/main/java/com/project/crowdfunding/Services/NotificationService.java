package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification sendNotification(Notification notification);
    List<Notification> getNotificationsForUser(Long userId);
    List<Notification> getUnreadNotifications(Long userId);
}
