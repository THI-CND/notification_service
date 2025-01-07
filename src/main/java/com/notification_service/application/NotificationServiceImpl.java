package com.notification_service.application;

import com.notification_service.adapter.in.rabbitmq.dto.NotificationRmqGenericMessageDto;
import com.notification_service.domain.NotificationFactory;
import com.notification_service.domain.NotificationService;
import com.notification_service.domain.models.Notification;
import com.notification_service.ports.outgoing.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationFactory factory;
    private final NotificationRepository repository;

    private static final String USER_COUNT = "user.count";

    public NotificationServiceImpl(NotificationFactory factory, NotificationRepository repository) {
        this.factory = factory;
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
        notification.ifPresent(n -> {
            n.setStatus(status);
            repository.save(n);
        });
        return notification;
    }

    @Override
    public void handleRmqMessage(NotificationRmqGenericMessageDto messageDto, String routingKey) {
        if (USER_COUNT.equals(routingKey)) {
            handleNotificationsForAllUsers(messageDto);
        } else {
            Notification notification = factory.createNotification(messageDto, routingKey);
            repository.save(notification);
        }
    }

    private void handleNotificationsForAllUsers(NotificationRmqGenericMessageDto messageDto) {
        List<String> allUsernames = repository.findAllUsernames();
        for (String username : allUsernames) {
            Notification notification = factory.createNotificationForUserCountUpdate(messageDto, username);
            repository.save(notification);
        }
    }
}
