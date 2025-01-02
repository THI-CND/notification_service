package com.notification_service.domain.models;

import lombok.*;

@Data
@AllArgsConstructor
public class Notification {

    private Long id;
    private String username;
    private String title;
    private String message;
    private NotificationStatus status;

    public enum NotificationStatus {
        READ, UNREAD, DELETED
    }
/*
    static public Notification createNotificationFromRecipe(json, String title, String message) {
        return new Notification(null, user, "neues rezept", message, NotificationStatus.UNREAD);
    }

 */
}