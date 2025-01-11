package com.notification_service.adapter.in.rest;

import com.notification_service.adapter.in.rest.dto.NotificationResponse;
import com.notification_service.domain.NotificationService;
import com.notification_service.domain.models.Notification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequestMapping("/api/v1/notifications")
@RestController
public class NotificationRestControllerV1 {

    private final NotificationService notificationService;

    public NotificationRestControllerV1(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationResponse> getAllNotifications (@RequestParam String username) {
        return notificationService.getNotifications(username)
                .stream().map(NotificationResponse::fromNotification)
                .toList();
    }

    @GetMapping ("/status/{status}")
    public List<NotificationResponse> getAllNotificationsByStatus(@RequestParam String username,
                                                                  @PathVariable Notification.NotificationStatus status) {
        return notificationService.getNotificationsByStatus(username, status)
                .stream()
                .map(NotificationResponse::fromNotification)
                .toList();
    }

    @GetMapping ("/{id}")
    public NotificationResponse getNotificationById(@RequestParam String username,
                                                    @PathVariable Long id) {
        var notification = notificationService.getNotificationById(id);
        if (notification.isPresent()) {
            if (notification.get().getUsername().equals(username)) {
                return NotificationResponse.fromNotification(notification.get());
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this notification");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found");
        }
    }
}