package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Notification;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Exception.ResourceNotFoundException;
import com.project.crowdfunding.Repository.NotificationRepository;
import com.project.crowdfunding.Services.Redis.RedisPublisher;
import com.project.crowdfunding.dto.request.NotificationRequestDto;
import com.project.crowdfunding.dto.response.NotificationResponseDto;
import com.project.crowdfunding.utils.AuthHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final AuthHelper authHelper;

    private final RedisPublisher redisPublisher;

    @Override
    public List<NotificationResponseDto> getAllNotifications() {
        List<Notification> notification = notificationRepository.findAll();
        return notification.stream().map(notification1 -> modelMapper.map(notification1, NotificationResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDto sendNotification(NotificationRequestDto notificationDto) {
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
            log.warn("Error publishing notification : {}", e.getMessage());
        }

        return modelMapper.map(savedNotification, NotificationResponseDto.class);
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
    public List<NotificationResponseDto> getNotificationsForUser(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserUserId(userId);
        return notifications.stream().map(notification -> modelMapper.map(notification, NotificationResponseDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<NotificationResponseDto> getUnreadNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserUserIdAndReadFalse(userId);
        return notifications.stream().map(notification -> modelMapper.map(notification, NotificationResponseDto.class)).collect(Collectors.toList());

    }

    @Override
    public NotificationResponseDto getNotificationById(Long id) {
        Notification notifications = notificationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notification not found!"));
        return  modelMapper.map(notifications, NotificationResponseDto.class);

    }

    @Override
    public void deleteNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notification not found!"));
        notificationRepository.delete(notification);
    }

    @Transactional
    @Override
    public NotificationResponseDto changeStatusById(Long notificationId, boolean isRead) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(()-> new ResourceNotFoundException("Notification not found!"));
        String admin = authHelper.getAuthenticatedUsername();

        notification.setRead(isRead);
        Notification savedNotification = notificationRepository.save(notification);
        return modelMapper.map(savedNotification, NotificationResponseDto.class);
    }
}
