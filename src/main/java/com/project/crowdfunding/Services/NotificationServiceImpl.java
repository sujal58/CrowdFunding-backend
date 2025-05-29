package com.project.crowdfunding.Services;

import com.project.crowdfunding.Entity.Notification;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Enums.NotificationType;
import com.project.crowdfunding.Exception.ResourceNotFoundException;
import com.project.crowdfunding.Repository.NotificationRepository;
import com.project.crowdfunding.dto.request.NotificationRequestDto;
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

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification sendNotification(NotificationRequestDto notificationDto) {
        String username = authHelper.getAuthenticatedUsername();
        User user = userService.getByUsername(username);

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setNotificationType(NotificationType.fromString(notificationDto.getNotificationType()));
        notification.setMessage(notificationDto.getMessage());

        return notificationRepository.save(notification);
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
