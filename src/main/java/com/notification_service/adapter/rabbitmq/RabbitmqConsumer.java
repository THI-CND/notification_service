package com.notification_service.adapter.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.notification_service.domain.NSService;
import com.notification_service.domain.models.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RabbitmqConsumer {
    private final NSService nsService;

    public RabbitmqConsumer(NSService nsService) {
        this.nsService = nsService;
    }
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConsumer.class);

    @RabbitListener(queues = "collection.updated")
    public void receiveMessage(Notification notification) {
        //public void receiveMessage(String notification) {
        logger.info("Empfangene Nachricht <{}>", notification);
        nsService.saveNotification(notification);
    }
}