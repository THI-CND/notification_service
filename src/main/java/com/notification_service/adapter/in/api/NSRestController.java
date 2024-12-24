package com.notification_service.adapter.in.api;


import com.notification_service.adapter.in.api.dto.NotificationRequest;
import com.notification_service.adapter.in.api.dto.NotificationResponse;
import com.notification_service.domain.NSService;
import com.notification_service.domain.models.Notification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequestMapping("/notifications")
@RestController
public class NSRestController {

    private final NSService nsService;

    public NSRestController(NSService nsService) {
        this.nsService = nsService;
    }

    @GetMapping
    public List<NotificationResponse> getAllNotifications (@RequestParam String username) {
        var notifications = nsService.getNotifications(username).stream().map(NotificationResponse::fromNotification).toList();
        if (notifications.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT); // 204 No Content
        }
        return notifications;
    }

    @GetMapping ("/status/{status}")
    public List<NotificationResponse> getAllNotificationsByStatus(@RequestParam String username, @PathVariable Notification.NotificationStatus status) {
        var notifications = nsService.getNotificationsByStatus(username, status)
                .stream()
                .map(NotificationResponse::fromNotification)
                .toList();
        if (notifications.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT); // 204 No Content
        }
        return notifications;
    }

    @GetMapping ("/{id}")
    public NotificationResponse getNotificationById(@RequestParam String username, @PathVariable Long id) {
        var notification = nsService.getNotificationById(id);
        if (notification.isPresent()) {
            if (notification.get().getUser().equals(username)) {
                return NotificationResponse.fromNotification(notification.get());
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this notification");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found");
        }
    }

    @PutMapping ("/{id}")
    public NotificationResponse updateNotificationStatus(@RequestParam String username, @PathVariable Long id, @RequestBody NotificationRequest request) {
        var notification = nsService.updateNotificationStatus(id, request.getStatus());
        if (notification.isPresent()) {
            if (notification.get().getUser().equals(username)) {
                return NotificationResponse.fromNotification(notification.get());
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this notification");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found");
        }
    }
}