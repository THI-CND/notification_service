package com.notification_service;

import com.notification_service.application.NSServiceImpl;
import com.notification_service.domain.models.Notification;
import com.notification_service.ports.outgoing.NSRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NSServiceImplTest {

    private NSRepository repository;
    private NSServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(NSRepository.class);
        service = new NSServiceImpl(repository);
    }

    @Test
    void testGetNotifications() {
        String username = "Testuser1";
        List<Notification> mockNotifications = Arrays.asList(
                new Notification(1L, "Testuser1", "Titel1", "Nachricht1"),
                new Notification(2L, "Testuser1", "Titel2", "Nachricht2")
        );
        when(repository.findByUser(username)).thenReturn(mockNotifications);

        List<Notification> notifications = service.getNotifications(username);

        assertEquals(2, notifications.size());
        //Test if the method findByUser was called exactly once
        verify(repository, times(1)).findByUser(username);
    }

    @Test
    void testSaveNotification() {
        Notification notification = new Notification(1L, "Testuser1", "Titel", "Nachricht");

        service.saveNotification(notification);

        verify(repository, times(1)).save(notification);
    }
}