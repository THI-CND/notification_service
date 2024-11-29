package com.notification_service.adapter.jpa;

import com.notification_service.adapter.jpa.entities.NotificationEntity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaNSRepository extends CrudRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByUsername(String username);
}