package com.notification_service.adapter.out.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.notification_service.adapter.out.jpa.entities.NotificationEntity;
import com.notification_service.domain.models.Notification;
import com.notification_service.ports.outgoing.NSRepository;

@Service
public class JpaNSRepositoryIm implements NSRepository {

    private final JpaNSRepository repo;

    public JpaNSRepositoryIm(JpaNSRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Notification> findByUser(String username) {
        return repo.findByUsername(username).stream().map(NotificationEntity::toNotification).toList();
    }

    @Override
    public void save(Notification notification) {
        NotificationEntity entity = new NotificationEntity(notification.getUser(), notification.getTitle(), notification.getMessage(), NotificationEntity.NotificationStatus.UNREAD);
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

    @Override
    public Optional<Notification> updateStatus(Long id, Notification.NotificationStatus status) {
        return repo.findById(id).map(entity -> {
            entity.setStatus(NotificationEntity.NotificationStatus.valueOf(status.name()));
            return repo.save(entity).toNotification();
        });
    }

}

