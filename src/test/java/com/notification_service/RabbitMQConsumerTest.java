package com.notification_service;

import com.notification_service.adapter.in.rabbitmq.NotificationRmqConsumer;
import com.notification_service.adapter.in.rabbitmq.dto.CollectionDto;
import com.notification_service.adapter.in.rabbitmq.dto.ReviewDto;
import com.notification_service.adapter.in.rabbitmq.dto.UserDto;
import com.notification_service.domain.NotificationService;
import com.notification_service.domain.UserService;
import com.notification_service.domain.models.Notification;
import com.notification_service.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class RabbitMQConsumerTest {

    private NotificationService notificationService;
    private UserService userService;
    private NotificationRmqConsumer consumer;

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        userService = mock(UserService.class);
        consumer = new NotificationRmqConsumer(notificationService, userService);
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
        List<User> users = List.of(new User("testUser1"), new User("testUser2"));
        when(userService.getAllUsers()).thenReturn(users);
        Notification notification = new Notification(1L, "testUser1", "Title", "Message", Notification.NotificationStatus.UNREAD);
        when(messageDto.createUserCountNotification(anyString())).thenReturn(notification);

        consumer.handleUserMessages(messageDto, "users.count");

        verify(notificationService, times(2)).saveNotification(notification);
    }

    @Test
    void testHandleUserMessagesWithNonMatchingUserCount() {
        UserDto messageDto = mock(UserDto.class);
        when(messageDto.getUserCount()).thenReturn(4);

        consumer.handleUserMessages(messageDto, "users.count");

        verify(notificationService, never()).saveNotification(any());
    }
}