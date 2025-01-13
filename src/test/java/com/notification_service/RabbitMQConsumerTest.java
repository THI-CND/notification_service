package com.notification_service;

import com.notification_service.adapter.in.rabbitmq.NotificationRmqConsumer;
import com.notification_service.adapter.in.rabbitmq.dto.CollectionDto;
import com.notification_service.adapter.in.rabbitmq.dto.ReviewDto;
import com.notification_service.adapter.in.rabbitmq.dto.UserDto;
import com.notification_service.domain.NotificationService;
import com.notification_service.domain.models.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class RabbitMQConsumerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationRmqConsumer consumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleCollectionMessages() {
        CollectionDto messageDto = mock(CollectionDto.class);
        Notification notification = new Notification(1L, "Testuser1", "Title", "Message", Notification.NotificationStatus.UNREAD);

        when(messageDto.createCollectionCreatedNotification()).thenReturn(notification);

        consumer.handleCollectionMessages(messageDto, "collection.created");

        verify(notificationService, times(1)).saveNotification(notification);
    }

    @Test
    void testHandleReviewMessages() {
        ReviewDto messageDto = mock(ReviewDto.class);
        Notification notification = new Notification(1L, "Testuser1", "Title", "Message", Notification.NotificationStatus.UNREAD);

        when(messageDto.createReviewCreatedNotification()).thenReturn(notification);

        consumer.handleReviewMessages(messageDto, "review.created");

        verify(notificationService, times(1)).saveNotification(notification);
    }

    @Test
    void testHandleUserMessages() {
        UserDto messageDto = mock(UserDto.class);
        when(messageDto.getUserCount()).thenReturn(5);
        Notification notification = new Notification(1L, "Testuser1", "Title", "Message", Notification.NotificationStatus.UNREAD);

        when(notificationService.getAllUsernames()).thenReturn(List.of("Testuser1"));
        when(messageDto.createUserCountNotification("Testuser1")).thenReturn(notification);

        consumer.handleUserMessages(messageDto, "users.count");

        verify(notificationService, times(1)).saveNotification(notification);
    }
}