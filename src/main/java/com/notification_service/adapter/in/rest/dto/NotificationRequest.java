package com.notification_service.adapter.in.rest.dto;

import com.notification_service.domain.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {

    @NonNull
    private Notification.NotificationStatus status;
}
