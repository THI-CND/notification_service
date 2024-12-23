package com.notification_service.application;

import com.notification_service.domain.NSService;
import com.notification_service.domain.models.Notification;
import com.notification_service.ports.outgoing.NSRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NSServiceImpl implements NSService {

    private final NSRepository repository;

    public NSServiceImpl(NSRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public List<Notification> getNotifications(String username) {
        return repository.findByUser(username);
    }

    @Override
    public void saveNotification(Notification notification) {
        repository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByStatus(String username, Notification.NotificationStatus status) {
        return repository.findByUserAndStatus(username, status);
    }
}
