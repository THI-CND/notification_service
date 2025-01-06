package com.notification_service.adapter.out.jpa;

import com.notification_service.adapter.out.jpa.entities.NotificationEntity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationJpaRepository extends CrudRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByUsername(String username);
    List<NotificationEntity> findByUsernameAndStatus(String username, NotificationEntity.NotificationStatus status);
    @Query("SELECT DISTINCT n.username FROM NotificationEntity n")
    List<String> findAllUsernames();
}