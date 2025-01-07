package com.notification_service.domain;

import com.notification_service.adapter.in.rabbitmq.dto.NotificationRmqGenericMessageDto;
import com.notification_service.domain.models.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationFactory {

    private static final String COLLECTION_CREATED = "collection.created";
    private static final String COLLECTION_UPDATED = "collection.updated";
    private static final String COLLECTION_DELETED = "collection.deleted";
    private static final String REVIEW_CREATED = "review.created";

    public Notification createNotification(NotificationRmqGenericMessageDto messageDto, String routingKey) {
        String username = (String) messageDto.getAuthor();

        if (username == null || messageDto.getName() == null) {
            throw new IllegalArgumentException("The messaage does not contain the necessary fields");
        }

        String title;
        String message = switch (routingKey) {
            case COLLECTION_CREATED -> {
                title = "Collection created!";
                yield "Hello " + username + ", your new collection \"" + messageDto.getName() + "\" has been created successfully!";
            }
            case COLLECTION_UPDATED -> {
                title = "Collection updated!";
                yield "Hello " + username + ", your collection \"" + messageDto.getName()  + "\" has been updated successfully!";
            }
            case COLLECTION_DELETED -> {
                title = "Collection deleted!";
                yield "Hello " + username + ", your collection \"" + messageDto.getName()  + "\" has been deleted successfully!";
            }
            case REVIEW_CREATED -> {
                title = "Review created!";
                yield "Hello " + username + ", your review has been created successfully!";
            }
            default -> throw new IllegalArgumentException("Unknown routing key: " + routingKey);
        };

        return new Notification(
                null,
                username,
                title,
                message,
                Notification.NotificationStatus.UNREAD
        );
    }

    public Notification createNotificationForUserCountUpdate(NotificationRmqGenericMessageDto messageDto, String username) {
        String title = "User count updated!";
        String message = "Hello " + username + ", the number of users is now " + messageDto.getNumberOfUsers() + "!";

        return new Notification(
                null,
                username,
                title,
                message,
                Notification.NotificationStatus.UNREAD
        );
    }
}
