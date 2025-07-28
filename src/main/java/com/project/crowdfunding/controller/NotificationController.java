package com.project.crowdfunding.controller;


import com.project.crowdfunding.Entity.Notification;
import com.project.crowdfunding.Entity.User;
import com.project.crowdfunding.Services.NotificationServiceImpl;
import com.project.crowdfunding.Services.UserService;
import com.project.crowdfunding.dto.request.NotificationRequestDto;
import com.project.crowdfunding.dto.response.ApiResponse;
import com.project.crowdfunding.dto.response.NotificationResponseDto;
import com.project.crowdfunding.utils.AuthHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Endpoints to send, fetch, update, and delete user notifications")
public class NotificationController {

    private final NotificationServiceImpl notificationService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final HttpServletRequest servletRequest;
    private final AuthHelper authHelper;

    @Operation(
            summary = "Send Notification",
            description = "Sends a notification based on the provided details in the request body."
    )
    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody NotificationRequestDto request) {
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

    @Operation(
            summary = "Broadcast Notification",
            description = "Broadcast a notification to all users."
    )
    @PostMapping("/broadcast")
    public ResponseEntity<ApiResponse> broadcast(@Valid @RequestBody NotificationRequestDto request) {
        request.setUsername(null);
        notificationService.broadcastNotification(request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification broadcasted successfully!"
                )
        );
    }

    @Operation(
            summary = "Get All Notifications",
            description = "Retrieves all notifications from the system. Usually for administrative purposes."
    )
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

    @Operation(
            summary = "Get Notifications for Current User",
            description = "Fetches all notifications that belong to the currently authenticated user."
    )
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

    @Operation(
            summary = "Get Unread Notifications for Current User",
            description = "Retrieves unread notifications for the currently authenticated user."
    )
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
        );
    }

    @Operation(
            summary = "Get Notification by ID",
            description = "Fetches a specific notification by its unique ID."
    )
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

    @Operation(
            summary = "Delete Notification",
            description = "Deletes a notification by its ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotificationById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification deleted successfully!"
                )
        );
    }

//    @Operation(
//        summary = "Update Notification",
//        description = "Updates an existing notification by ID with new details."
//    )
//    @PutMapping("/{id}")
//    public ResponseEntity<NotificationResponseDto> updateNotification(
//            @PathVariable Long id,
//            @Valid @RequestBody NotificationRequestDto dto) {
//        Notification updated = notificationService.updateNotification(id, dto);
//        return ResponseEntity.ok(modelMapper.map(updated, NotificationResponseDto.class));
//    }
}
