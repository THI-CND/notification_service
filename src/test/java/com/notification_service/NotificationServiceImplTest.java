package com.notification_service;


import com.notification_service.application.NotificationServiceImpl;
import com.notification_service.domain.models.Notification;
import com.notification_service.ports.out.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class NotificationServiceImplTest {

    private NotificationRepository repository;
    private NotificationServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(NotificationRepository.class);
        service = new NotificationServiceImpl(repository);
    }

    @Test
    void getNotifications_ReturnsNotifications() {
        String username = "testUser";
        List<Notification> notifications = List.of(
                new Notification(1L, username, "Title", "Message", Notification.NotificationStatus.UNREAD)
        );
        when(repository.findByUser(username)).thenReturn(notifications);

        List<Notification> result = service.getNotifications(username);

        assertEquals(1, result.size());
        assertEquals("Title", result.getFirst().getTitle());
        verify(repository, times(1)).findByUser(username);
    }

    @Test
    void getNotificationById_ReturnsNotification() {
        Long id = 1L;
        Notification notification = new Notification(id, "testUser", "Title", "Message", Notification.NotificationStatus.UNREAD);
        when(repository.findById(id)).thenReturn(Optional.of(notification));

        Optional<Notification> result = service.getNotificationById(id);

        assertTrue(result.isPresent());
        assertEquals("Title", result.get().getTitle());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void updateNotificationStatus_UpdatesStatus() {
        Long id = 1L;
        Notification notification = new Notification(id, "testUser", "Title", "Message", Notification.NotificationStatus.UNREAD);
        when(repository.findById(id)).thenReturn(Optional.of(notification));

        service.updateNotificationStatus(id, Notification.NotificationStatus.READ);

        assertEquals(Notification.NotificationStatus.READ, notification.getStatus());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(notification);
    }

    @Test
    void saveNotification_SavesNotification() {
        Notification notification = new Notification(1L, "testUser", "Title", "Message", Notification.NotificationStatus.UNREAD);

        service.saveNotification(notification);

        verify(repository, times(1)).save(notification);
    }

    @Test
    void getAllUsernames_ReturnsUsernames() {
        List<String> usernames = List.of("testUser", "otherUser");
        when(repository.findAllUsernames()).thenReturn(usernames);

        List<String> result = service.getAllUsernames();

        assertEquals(2, result.size());
        assertEquals("testUser", result.get(0));
        assertEquals("otherUser", result.get(1));
        verify(repository, times(1)).findAllUsernames();
    }

    @Test
    void getNotificationsByStatus_ReturnsNotifications() {
        String username = "testUser";
        Notification.NotificationStatus status = Notification.NotificationStatus.UNREAD;
        List<Notification> notifications = List.of(
                new Notification(1L, username, "Title", "Message", status)
        );
        when(repository.findByUserAndStatus(username, status)).thenReturn(notifications);

        List<Notification> result = service.getNotificationsByStatus(username, status);

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
        verify(repository, times(1)).findByUserAndStatus(username, status);
    }
}
