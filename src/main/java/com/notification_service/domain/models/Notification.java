package com.notification_service.domain.models;

import lombok.*;

@Data
@AllArgsConstructor
public class Notification {

    private Long id;
    private String user;
    private String title;
    private String message;
    private NotificationStatus status;

    public enum NotificationStatus {
        READ, UNREAD, DELETED
    }
}