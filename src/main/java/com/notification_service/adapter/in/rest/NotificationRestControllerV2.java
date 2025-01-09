package com.notification_service.adapter.in.rest;


import com.notification_service.adapter.in.rest.dto.NotificationRequest;
import com.notification_service.adapter.in.rest.dto.NotificationResponse;
import com.notification_service.domain.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RequestMapping("/api/v2/notifications")
@RestController
public class NotificationRestControllerV2 {

    private final NotificationService notificationService;

    public NotificationRestControllerV2(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PutMapping ("/{id}")
    public NotificationResponse updateNotificationStatus(@RequestParam String username,
                                                         @PathVariable Long id,
                                                         @RequestBody NotificationRequest request) {
        var notification = notificationService.updateNotificationStatus(id, request.getStatus());
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