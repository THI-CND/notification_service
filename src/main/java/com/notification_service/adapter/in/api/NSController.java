package com.notification_service.adapter.in.api;


import com.notification_service.adapter.in.api.dto.NotificationResponse;
import com.notification_service.domain.NSService;
import com.notification_service.domain.models.Notification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequestMapping("/notifications")
@RestController
public class NSController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NSController.class);

    private final NSService nsService;

    public NSController(NSService nsService) {
        this.nsService = nsService;
    }

    @GetMapping
    public List<NotificationResponse> getAllNotifications (@RequestParam String username) {
        logger.info("aufgerufener username war:" + username);
        return nsService.getNotifications(username).stream().map(NotificationResponse::fromNotification).toList();
    }

    @GetMapping ("/status/{status}")
    public List<NotificationResponse> getAllNotificationsByStatus(@RequestParam String username, @PathVariable Notification.NotificationStatus status) {
        logger.info("aufgerufener username war:" + username + " und status war:" + status);
        return nsService.getNotificationsByStatus(username, status).stream().map(NotificationResponse::fromNotification).toList();
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

    @PutMapping ("/{id}/status/{status}")
    public NotificationResponse updateNotificationStatus(@RequestParam String username, @PathVariable Long id, @PathVariable Notification.NotificationStatus status) {
        var notification = nsService.updateNotificationStatus(id, status);
        logger.info("Notification war:" + notification);
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