package com.notification_service;


import com.notification_service.application.NotificationServiceImpl;
import com.notification_service.domain.models.Notification;
import com.notification_service.domain.models.User;
import com.notification_service.ports.out.NotificationRepository;
import com.notification_service.ports.out.UserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class NotificationServiceImplTest {

    private NotificationRepository notificationRepository;
    private UserProvider userProvider;
    private NotificationServiceImpl service;

    @BeforeEach
    void setUp() {
        notificationRepository = mock(NotificationRepository.class);
        userProvider = mock(UserProvider.class);
        service = new NotificationServiceImpl(notificationRepository, userProvider);
    }

    @Test
    void getNotifications_ReturnsNotifications() {
        String username = "testUser";
        List<Notification> notifications = List.of(
                new Notification(1L, username, "Title", "Message", Notification.NotificationStatus.UNREAD)
        );
        when(notificationRepository.findByUser(username)).thenReturn(notifications);

        List<Notification> result = service.getNotifications(username);

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
        verify(notificationRepository, times(1)).findByUser(username);
    }

    @Test
    void getNotificationById_ReturnsNotification() {
        Long id = 1L;
        Notification notification = new Notification(id, "testUser", "Title", "Message", Notification.NotificationStatus.UNREAD);
        when(notificationRepository.findById(id)).thenReturn(Optional.of(notification));

        Optional<Notification> result = service.getNotificationById(id);

        assertTrue(result.isPresent());
        assertEquals("Title", result.get().getTitle());
        verify(notificationRepository, times(1)).findById(id);
    }

    @Test
    void updateNotificationStatus_UpdatesStatus() {
        Long id = 1L;
        Notification notification = new Notification(id, "testUser", "Title", "Message", Notification.NotificationStatus.UNREAD);
        when(notificationRepository.findById(id)).thenReturn(Optional.of(notification));

        service.updateNotificationStatus(id, Notification.NotificationStatus.READ);

        assertEquals(Notification.NotificationStatus.READ, notification.getStatus());
        verify(notificationRepository, times(1)).findById(id);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void saveNotification_SavesNotification() {
        Notification notification = new Notification(1L, "testUser", "Title", "Message", Notification.NotificationStatus.UNREAD);

        service.saveNotification(notification);

        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void getAllUsers_ReturnsUsers() {
        List<User> users = List.of(new User("testUser"), new User("otherUser"));
        when(userProvider.listUsers()).thenReturn(users);

        List<User> result = service.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("testUser", result.get(0).getUsername());
        assertEquals("otherUser", result.get(1).getUsername());
        verify(userProvider, times(1)).listUsers();
    }

    @Test
    void getNotificationsByStatus_ReturnsNotifications() {
        String username = "testUser";
        Notification.NotificationStatus status = Notification.NotificationStatus.UNREAD;
        List<Notification> notifications = List.of(
                new Notification(1L, username, "Title", "Message", status)
        );
        when(notificationRepository.findByUserAndStatus(username, status)).thenReturn(notifications);

        List<Notification> result = service.getNotificationsByStatus(username, status);

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
        verify(notificationRepository, times(1)).findByUserAndStatus(username, status);
    }
}
