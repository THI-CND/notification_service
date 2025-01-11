package com.notification_service.adapter.in.rabbitmq.dto;

import com.notification_service.domain.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private int userCount;


    public Notification createUserCountNotification(String username) {
        String title = "User count updated!";
        String message = "Hello " + username + ", the number of users is now " + userCount + "!";
        return new Notification(null, username, title, message, Notification.NotificationStatus.UNREAD);
    }
}