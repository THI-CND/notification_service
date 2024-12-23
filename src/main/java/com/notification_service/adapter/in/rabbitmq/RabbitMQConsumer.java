package com.notification_service.adapter.in.rabbitmq;

import com.notification_service.adapter.in.rabbitmq.dto.NotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.notification_service.domain.NSService;
import com.notification_service.domain.models.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RabbitMQConsumer {
    private final NSService nsService;

    public RabbitMQConsumer(NSService nsService) {
        this.nsService = nsService;
    }
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = {"collection.updated","collection.deleted", "collection.created"})
    public void receiveMessage(NotificationMessage notificationMessage) {
        logger.info("Empfangene Nachricht <{}>", notificationMessage);
        nsService.saveNotification(new Notification(null, notificationMessage.getUser(), notificationMessage.getTitle(), notificationMessage.getMessage(), notificationMessage.getStatus()));
    }
}