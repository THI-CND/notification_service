/*package com.notification_service;

import com.notification_service.adapter.in.rabbitmq.RabbitMQConsumer;
import com.notification_service.domain.NSService;
import com.notification_service.domain.models.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RabbitMQConsumerTest {

    private NSService nsService;
    private RabbitMQConsumer consumer;

    @BeforeEach
    void setUp() {
        nsService = mock(NSService.class);
        consumer = new RabbitMQConsumer(nsService);
    }

    @Test
    void testReceiveMessage() {
        Notification notification = new Notification(1L, "Testuser1", "Titel", "Nachricht");

        consumer.receiveMessage(notification);

        verify(nsService, times(1)).saveNotification(notification);
    }
}
*/