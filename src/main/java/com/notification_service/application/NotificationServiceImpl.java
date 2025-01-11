package com.notification_service.application;

import com.notification_service.domain.NotificationService;
import com.notification_service.domain.models.Notification;
import com.notification_service.ports.outgoing.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public List<Notification> getNotifications(String username) {
        return repository.findByUser(username);
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
        Optional<Notification> notification = repository.findById(id);
        if (notification.isPresent()) {
            Notification n = notification.get();
            n.setStatus(status);
            repository.save(n);
        }
        return notification;
    }

    @Override
    public void saveNotification(Notification notification) {
        repository.save(notification);
    }

    @Override
    public List<String> getAllUsernames() {
        return repository.findAllUsernames();
    }

}
