package com.notification_service.application;

import com.notification_service.domain.NotificationFactory;
import com.notification_service.domain.NotificationService;
import com.notification_service.domain.models.Notification;
import com.notification_service.ports.outgoing.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationFactory factory;
    private final NotificationRepository repository;

    public NotificationServiceImpl(NotificationFactory factory, NotificationRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }
    
    @Override
    public List<Notification> getNotifications(String username) {
        return repository.findByUser(username);
    }

    @Override
    public void generateAndSaveNotification(Map<String, Object> payload, String routingKey) {
        Notification notification = null;
        Map<String, String> notificationContent = null;


        if ("collection.created".equals(routingKey)) {
            notification = factory.collectionCreatedNotification(payload);
        } else if ("collection.updated".equals(routingKey)) {
            notification = factory.collectionUpdatedNotification(payload);
        } else if ("collection.deleted".equals(routingKey)) {
            notification = factory.collectionDeletedNotification(payload);
        } else if ("review.created".equals(routingKey)) {
            notification = factory.reviewCreatedNotification(payload);
        } else if ("user.count".equals(routingKey)) {
            notificationContent = factory.numberOfUsersNotification(payload);

            List<String> allUsernames = repository.findAllUsernames();
            for (String username : allUsernames) {
                if (username != null && !username.isEmpty()) {
                    Notification userNotification = new Notification(
                            null, username, notificationContent.get("title"), notificationContent.get("message"), Notification.NotificationStatus.UNREAD
                    );
                    repository.save(userNotification);
                }
            }
        }
        if (notification != null) {
            repository.save(notification);
        }
    }

    @Override
    public List<Notification> getNotificationsByStatus(String username, Notification.NotificationStatus status) {
        return repository.findByUserAndStatus(username, status);
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Notification> updateNotificationStatus(Long id, Notification.NotificationStatus status) {
        return repository.updateStatus(id, status);
    }
}
