/*package com.notification_service;

import com.notification_service.adapter.out.jpa.JpaNSRepository;
import com.notification_service.adapter.out.jpa.JpaNSRepositoryIm;
import com.notification_service.adapter.out.jpa.entities.NotificationEntity;
import com.notification_service.domain.models.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

// Mockito für Unit-Tests, Mockmvc für Integrationstests (HTTP-Schnittstellen)

class JpaNSRepositoryImTest {

    private JpaNSRepository repo;
    private JpaNSRepositoryIm repositoryImpl;

    @BeforeEach
    void setUp() {
        repo = mock(JpaNSRepository.class);
        repositoryImpl = new JpaNSRepositoryIm(repo);
    }

    @Test
    void testFindByUser() {
        String username = "Testuser1";
        List<NotificationEntity> mockEntities = Arrays.asList(
                new NotificationEntity("Testuser1", "Titel1", "Nachricht1"),
                new NotificationEntity("Testuser1", "Titel2", "Nachricht2")
        );
        when(repo.findByUsername(username)).thenReturn(mockEntities);

        List<Notification> notifications = repositoryImpl.findByUser(username);

        assertEquals(2, notifications.size());
        assertEquals("Titel1", notifications.get(0).getTitle());
        verify(repo, times(1)).findByUsername(username);
    }

    @Test
    void testSave() {
        Notification notification = new Notification(1L, "Testuser1", "Titel", "Nachricht");

        repositoryImpl.save(notification);

        verify(repo, times(1)).save(any(NotificationEntity.class));
    }
}
 */