package com.notification_service;

import com.notification_service.adapter.in.rest.NotificationRestControllerV2;
import com.notification_service.adapter.in.rest.dto.NotificationRequest;
import com.notification_service.adapter.in.rest.dto.NotificationResponse;
import com.notification_service.domain.NotificationService;
import com.notification_service.domain.models.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class NotificationRestControllerV2Test {

    private NotificationService notificationService;
    private NotificationRestControllerV2 controller;

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        controller = new NotificationRestControllerV2(notificationService);
    }

    @Test
    void updateNotificationStatus_ThrowsNotFoundException() {
        Long id = 1L;
        String username = "testUser";
        NotificationRequest request = new NotificationRequest(Notification.NotificationStatus.READ);
        when(notificationService.updateNotificationStatus(id, request.getStatus()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                controller.updateNotificationStatus(username, id, request)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(notificationService, times(1)).updateNotificationStatus(id, request.getStatus());
    }

    @Test
    void updateNotificationStatus_ThrowsForbiddenException() {
        Long id = 1L;
        String username = "testUser";
        NotificationRequest request = new NotificationRequest(Notification.NotificationStatus.READ);
        when(notificationService.updateNotificationStatus(id, request.getStatus()))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this notification"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                controller.updateNotificationStatus(username, id, request)
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        verify(notificationService, times(1)).updateNotificationStatus(id, request.getStatus());
    }

    @Test
    void updateNotificationStatus_ReturnsNotification() {
        Long id = 1L;
        String username = "testUser";
        Notification notification = new Notification(id, username, "Title", "Message", Notification.NotificationStatus.UNREAD);
        NotificationRequest request = new NotificationRequest(Notification.NotificationStatus.READ);
        when(notificationService.updateNotificationStatus(id, request.getStatus())).thenReturn(Optional.of(notification));

        NotificationResponse result = controller.updateNotificationStatus(username, id, request);

        assertEquals("Title", result.getTitle());
        verify(notificationService, times(1)).updateNotificationStatus(id, request.getStatus());
    }
}