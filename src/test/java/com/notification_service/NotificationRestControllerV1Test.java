package com.notification_service;

import com.notification_service.adapter.in.rest.NotificationRestControllerV1;
import com.notification_service.adapter.in.rest.dto.NotificationResponse;
import com.notification_service.domain.NotificationService;
import com.notification_service.domain.models.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationRestControllerV1Test {

    private NotificationService notificationService;
    private NotificationRestControllerV1 controller;

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        controller = new NotificationRestControllerV1(notificationService);
    }

    @Test
    void getAllNotifications_ReturnsList() {
        String username = "testUser";
        List<Notification> notifications = List.of(
                new Notification(1L, username, "Title 1", "Message 1", Notification.NotificationStatus.UNREAD)
        );
        when(notificationService.getNotifications(username)).thenReturn(notifications);

        List<NotificationResponse> result = controller.getAllNotifications(username);

        assertEquals(1, result.size());
        assertEquals("Title 1", result.get(0).getTitle());
        verify(notificationService, times(1)).getNotifications(username);
    }

    @Test
    void getNotificationById_ThrowsNotFoundException() {
        Long id = 1L;
        String username = "testUser";
        when(notificationService.getNotificationById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                controller.getNotificationById(username, id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(notificationService, times(1)).getNotificationById(id);
    }

    @Test
    void getNotificationById_ThrowsForbiddenException() {
        Long id = 1L;
        String username = "testUser";
        Notification otherUserNotification = new Notification(id, "otherUser", "Title", "Message", Notification.NotificationStatus.UNREAD);
        when(notificationService.getNotificationById(id)).thenReturn(Optional.of(otherUserNotification));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                controller.getNotificationById(username, id)
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        verify(notificationService, times(1)).getNotificationById(id);
    }

    @Test
    void getNotificationById_ReturnsNotificationResponse() {
        Long id = 1L;
        String username = "testUser";
        Notification notification = new Notification(id, username, "Title", "Message", Notification.NotificationStatus.UNREAD);
        when(notificationService.getNotificationById(id)).thenReturn(Optional.of(notification));

        NotificationResponse result = controller.getNotificationById(username, id);

        assertEquals("Title", result.getTitle());
        assertEquals("Message", result.getMessage());
        verify(notificationService, times(1)).getNotificationById(id);
    }
}