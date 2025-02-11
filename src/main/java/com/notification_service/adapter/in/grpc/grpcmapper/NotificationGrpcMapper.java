package com.notification_service.adapter.in.grpc.grpcmapper;

import com.notification_service.domain.models.Notification;
import com.notification_service.stubs.*;

import java.util.List;

public class NotificationGrpcMapper {

    public static GetAllNotificationsResponse toGetAllNotificationsResponse(List<Notification> notifications) {
        return GetAllNotificationsResponse.newBuilder()
                .addAllNotifications(
                        notifications.stream()
                                .map(NotificationGrpcMapper::toNotificationResponse)
                                .toList()
                )
                .build();
    }

    public static NotificationResponse toNotificationResponse(Notification notification) {
        NotificationResponse.Builder builder = NotificationResponse.newBuilder()
                .setId(notification.getId())
                .setUsername(notification.getUsername())
                .setTitle(notification.getTitle())
                .setStatus(NotificationStatus.valueOf(notification.getStatus().name()));
        if (notification.getMessage() != null) {
            builder.setMessage(notification.getMessage());
        }

        return builder.build();
    }
}