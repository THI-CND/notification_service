package com.notification_service.adapter.in.rabbitmq;

import com.notification_service.adapter.in.rabbitmq.dto.CollectionDto;
import com.notification_service.adapter.in.rabbitmq.dto.ReviewDto;
import com.notification_service.adapter.in.rabbitmq.dto.UserDto;
import com.notification_service.domain.UserService;
import com.notification_service.domain.models.Notification;
import com.notification_service.domain.models.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.notification_service.domain.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class NotificationRmqConsumer {
    private final NotificationService notificationService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(NotificationRmqConsumer.class);

    public NotificationRmqConsumer(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.collection}")
    public void handleCollectionMessages(@Payload CollectionDto messageDto, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        try {
            Notification notification;

            switch (routingKey) {
                case "collection.created" -> notification = messageDto.createCollectionCreatedNotification();
                case "collection.updated" -> notification = messageDto.createCollectionUpdatedNotification();
                case "collection.deleted" -> notification = messageDto.createCollectionDeletedNotification();
                default -> throw new IllegalArgumentException("Unknown routing key: " + routingKey);
            }

            notificationService.saveNotification(notification);
            logger.info("Notification processed and saved: {}", notification);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid message: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.review}")
    public void handleReviewMessages(ReviewDto messageDto, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        try {
            Notification notification;

            if ("review.created".equals(routingKey)) {
                notification = messageDto.createReviewCreatedNotification();
            } else {
                throw new IllegalArgumentException("Unknown routing key: " + routingKey);
            }

            notificationService.saveNotification(notification);
            logger.info("Notification processed and saved for Review: {}", notification);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid message for Review: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error processing message for Review: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.user}")
    public void handleUserMessages(UserDto messageDto, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        try {
            if ("users.count".equals(routingKey)) {
                // Send a notification to all users every 5th user
                if (messageDto.getUserCount() % 5 == 0) {
                    List<User> users = userService.getAllUsers();
                    List<String> usernames = users.stream().map(User::getUsername).toList();

                    for (String username : usernames) {
                        Notification notification = messageDto.createUserCountNotification(username);
                        notificationService.saveNotification(notification);
                        logger.info("Notification processed and saved for User: {}", username);
                    }
                }
            } else {
                throw new IllegalArgumentException("Unknown routing key: " + routingKey);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid message for User: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error processing message for User: {}", e.getMessage(), e);
        }
    }
}