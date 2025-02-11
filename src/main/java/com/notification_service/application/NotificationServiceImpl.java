package com.notification_service.application;

import com.notification_service.domain.NotificationService;
import com.notification_service.domain.UserService;
import com.notification_service.domain.models.Notification;
import com.notification_service.domain.models.User;
import com.notification_service.ports.out.NotificationRepository;
import com.notification_service.ports.out.UserProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService, UserService {

    private final NotificationRepository repository;
    private final UserProvider userProvider;

    public NotificationServiceImpl(NotificationRepository repository, UserProvider userPovider) {
        this.repository = repository;
        this.userProvider = userPovider;
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
    public List<User> getAllUsers() {
        return userProvider.listUsers();
    }
}
