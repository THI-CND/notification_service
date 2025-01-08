package com.notification_service.domain;

import com.notification_service.domain.models.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<Notification> getNotifications(String username);
    List<Notification> getNotificationsByStatus(String username, Notification.NotificationStatus status);
    Optional<Notification> getNotificationById(Long id);
    Optional<Notification> updateNotificationStatus(Long id, Notification.NotificationStatus status);
    void saveNotification(Notification notification);
    List<String> getAllUsernames();
}
