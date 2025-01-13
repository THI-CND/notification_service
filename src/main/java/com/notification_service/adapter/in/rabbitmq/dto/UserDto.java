package com.notification_service.adapter.in.rabbitmq.dto;

import com.notification_service.domain.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private int userCount;


    public Notification createUserCountNotification(String username) {
        String title = "Nutzerzahl aktualisiert!";
        String message = "Hallo " + username + ", die Zahl der Nutzer ist jetzt " + userCount + "!";
        return new Notification(null, username, title, message, Notification.NotificationStatus.UNREAD);
    }
}