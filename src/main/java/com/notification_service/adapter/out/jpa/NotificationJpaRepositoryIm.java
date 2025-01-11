package com.notification_service.adapter.out.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.notification_service.adapter.out.jpa.entities.NotificationEntity;
import com.notification_service.domain.models.Notification;
import com.notification_service.ports.outgoing.NotificationRepository;

@Service
public class NotificationJpaRepositoryIm implements NotificationRepository {

    private final NotificationJpaRepository repo;

    public NotificationJpaRepositoryIm(NotificationJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Notification> findByUser(String username) {
        return repo.findByUsername(username).stream().map(NotificationEntity::toNotification).toList();
    }

    @Override
    public List<String> findAllUsernames() {
        return repo.findAllUsernames();
    }

    @Override
    public void save(Notification notification) {
        NotificationEntity entity = NotificationEntity.fromNotification(notification);
        repo.save(entity);
    }

    @Override
    public List<Notification> findByUserAndStatus(String username, Notification.NotificationStatus status) {
        return repo.findByUsernameAndStatus(username, NotificationEntity.NotificationStatus.valueOf(status.name())).stream()
                .map(NotificationEntity::toNotification).toList();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return repo.findById(id).map(NotificationEntity::toNotification);
    }
}

