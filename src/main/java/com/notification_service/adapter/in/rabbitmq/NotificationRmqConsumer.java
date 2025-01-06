package com.notification_service.adapter.in.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import com.notification_service.domain.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class NotificationRmqConsumer {
    private final NotificationService notificationService;
    private static final Logger logger = LoggerFactory.getLogger(NotificationRmqConsumer.class);

    @Value("${rabbitmq.routing.keys}")
    private List<String> allowedRoutingKeys;

    public NotificationRmqConsumer(NotificationService nsService) {
        this.notificationService = nsService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(@Payload Map<String, Object> payload, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        try {
            logger.info("Received message <{}> and key <{}>", payload, key);
            if (allowedRoutingKeys.contains(key)) {
                logger.info("Processing message <{}>", payload);
                notificationService.generateAndSaveNotification(payload, key);
            } else {
                logger.info("Ignoring message <{}>", payload);
            }
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}