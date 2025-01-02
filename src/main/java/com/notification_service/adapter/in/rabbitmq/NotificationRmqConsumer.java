package com.notification_service.adapter.in.rabbitmq;

import com.notification_service.adapter.in.rabbitmq.dto.NotificationRmqMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.notification_service.domain.NotificationService;
import com.notification_service.domain.models.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationRmqConsumer {
    private final NotificationService nsService;

    public NotificationRmqConsumer(NotificationService nsService) {
        this.nsService = nsService;
    }
    private static final Logger logger = LoggerFactory.getLogger(NotificationRmqConsumer.class);

    @RabbitListener(queues = {"collection.updated","collection.deleted", "collection.created"})
    public void receiveMessage(NotificationRmqMessage notificationMessage) {
        logger.info("Empfangene Nachricht <{}>", notificationMessage);
        nsService.saveNotification(new Notification(null, notificationMessage.getUser(), notificationMessage.getTitle(), notificationMessage.getMessage(), notificationMessage.getStatus()));
    }
}