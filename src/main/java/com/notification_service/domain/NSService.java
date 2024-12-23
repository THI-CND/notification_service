package com.notification_service.domain;

import com.notification_service.domain.models.Notification;

import java.util.List;
import java.util.Optional;

public interface NSService {
    List<Notification> getNotifications(String username);
    void saveNotification(Notification notification);
    List<Notification> getNotificationsByStatus(String username, Notification.NotificationStatus status);
    Optional<Notification> getNotificationById(Long id);
}
