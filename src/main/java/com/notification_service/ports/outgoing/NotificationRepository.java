package com.notification_service.ports.outgoing;

import java.util.List;
import java.util.Optional;

import com.notification_service.domain.models.Notification;

public interface NotificationRepository {
    List<Notification> findByUser(String user);
    List<String> findAllUsernames();
    void save(Notification notification);
    List<Notification> findByUserAndStatus(String user, Notification.NotificationStatus status);
    Optional<Notification> findById(Long id);
}
