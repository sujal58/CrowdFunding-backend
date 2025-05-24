package com.project.crowdfunding.controller;


import com.project.crowdfunding.Entity.Notification;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Services.NotificationServiceImpl;
import com.project.crowdfunding.Services.UserService;
import com.project.crowdfunding.dto.request.NotificationRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import com.project.crowdfunding.dto.response.NotificationResponseDto;
import com.project.crowdfunding.utils.AuthHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final HttpServletRequest servletRequest;

    private final AuthHelper authHelper;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody NotificationRequestDto request) {
        String username = authHelper.getAuthenticatedUsername();
        User user = userService.getByUsername(username);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification sent successfully!",
                        notificationService.sendNotification(request),
                        servletRequest.getRequestURI()
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllNotifications() {

        List<Notification> notifications = notificationService.getAllNotifications();
        List<NotificationResponseDto> response = notifications.stream()
                .map(n -> modelMapper.map(n, NotificationResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All notifications fetched successfully!",
                        response,
                        servletRequest.getRequestURI()
                )
        );
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getAllNotificationsByUser() {
        String username = authHelper.getAuthenticatedUsername();
        User user = userService.getByUsername(username);
        List<Notification> notifications = notificationService.getNotificationsForUser(user.getUserId());
        List<NotificationResponseDto> response = notifications.stream()
                .map(n -> modelMapper.map(n, NotificationResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All notifications fetched successfully!",
                        response,
                        servletRequest.getRequestURI()
                )
        );
    }

    @GetMapping("/unread")
    public ResponseEntity<ApiResponse> getUnreadNotifications() {
        String username = authHelper.getAuthenticatedUsername();
        User user = userService.getByUsername(username);
        List<Notification> notifications = notificationService.getUnreadNotifications(user.getUserId());
        List<NotificationResponseDto> response = notifications.stream()
                .map(n -> modelMapper.map(n, NotificationResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Unread notifications fetched successfully!",
                        response,
                        servletRequest.getRequestURI()
                )
        );    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        NotificationResponseDto dto = modelMapper.map(notification, NotificationResponseDto.class);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification fetched successfully!",
                        dto,
                        servletRequest.getRequestURI()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotificationById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification deleted successfully!"
                )
        );
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<NotificationResponseDto> updateNotification(@PathVariable Long id,
//                                                                      @Valid @RequestBody NotificationRequestDto dto) {
//        Notification updated = notificationService.updateNotification(id, dto);
//        return ResponseEntity.ok(modelMapper.map(updated, NotificationResponseDto.class));
//    }
}
