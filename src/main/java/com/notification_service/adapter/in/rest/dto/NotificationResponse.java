package com.notification_service.adapter.in.rest.dto;

import com.notification_service.domain.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private Long id;
    private String username;
    private String title;
    private String message;
    private Notification.NotificationStatus status;

    public static NotificationResponse fromNotification(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getUsername(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getStatus());
    }
}
