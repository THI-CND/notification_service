package com.notification_service.adapter.in.rabbitmq;

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

    @RabbitListener(queues = "collection.updated")
    @RabbitListener(queues = "collection.created")
    @RabbitListener(queues = "collection.deleted")
    public void receiveMessage(Notification notification) {
        logger.info("Empfangene Nachricht <{}>", notification);
        nsService.saveNotification(notification);
    }
}