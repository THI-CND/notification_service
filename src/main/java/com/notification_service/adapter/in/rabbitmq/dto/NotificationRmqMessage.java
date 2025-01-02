package com.notification_service.adapter.in.rabbitmq.dto;

import com.notification_service.domain.models.Notification;
import lombok.Data;
import lombok.NonNull;

@Data
public class NotificationRmqMessage {
    @NonNull
    private String user;
    @NonNull
    private String title;
    private String message;
    private Notification.NotificationStatus status;
}
