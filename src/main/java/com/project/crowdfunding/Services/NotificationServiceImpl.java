package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Notification;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Exception.ResourceNotFoundException;
import com.project.crowdfunding.Repository.NotificationRepository;
import com.project.crowdfunding.Services.Redis.RedisPublisher;
import com.project.crowdfunding.dto.request.NotificationRequestDto;
import com.project.crowdfunding.dto.response.NotificationResponseDto;
import com.project.crowdfunding.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final AuthHelper authHelper;

    private final RedisPublisher redisPublisher;

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification sendNotification(NotificationRequestDto notificationDto) {
        User user = userService.getByUsername(notificationDto.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + notificationDto.getUsername());
        }

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setNotificationType(notificationDto.getNotificationType());
        notification.setMessage(notificationDto.getMessage());

        Notification savedNotification =  notificationRepository.save(notification);

        // Publish real-time WebSocket notification
        try {
            NotificationResponseDto webSocketNotification = modelMapper.map(savedNotification, NotificationResponseDto.class);
            webSocketNotification.setBroadcast(false);
            webSocketNotification.setUsername(notificationDto.getUsername());

            redisPublisher.publish(webSocketNotification);
        } catch (Exception e) {
            System.err.println("Error publishing WebSocket notification: " + e.getMessage());
        }

        return savedNotification;
    }

    @Override
    public void broadcastNotification(NotificationRequestDto notificationDto) {
        Notification notification = new Notification();
        notification.setUser(null);
        notification.setNotificationType(notificationDto.getNotificationType());
        notification.setMessage(notificationDto.getMessage());

        Notification savedNotification =  notificationRepository.save(notification);

        // Publish real-time WebSocket notification
        try {
            NotificationResponseDto webSocketNotification = modelMapper.map(savedNotification, NotificationResponseDto.class);
            webSocketNotification.setBroadcast(true);
            webSocketNotification.setUsername(notificationDto.getUsername());

            redisPublisher.publish(webSocketNotification);
        } catch (Exception e) {
            System.err.println("Error publishing WebSocket notification: " + e.getMessage());
        }
    }


    @Override
    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserUserIdAndReadFalse(userId);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notification not found!"));
    }

    @Override
    public void deleteNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notification not found!"));
        notificationRepository.delete(notification);
    }
}
