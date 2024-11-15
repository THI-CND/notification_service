package com.cnd.notification_service.application;

import com.cnd.notification_service.domain.NSService;
import com.cnd.notification_service.domain.models.Notification;
import com.cnd.notification_service.ports.outgoing.NSRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NSServiceImpl implements NSService {

    private final NSRepository repository;

    public NSServiceImpl(NSRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Notification> getAllCollections() {
        System.out.println(repository);
        return List.of();
    }
}