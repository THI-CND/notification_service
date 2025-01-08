package com.notification_service.adapter.in.rabbitmq.dto;

import com.notification_service.domain.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CollectionDto {
    Long id;
    private String name;
    private String author;
    private String description;
    private List<Long> recipes;


    public Notification createCollectionCreatedNotification() {
        validateFields();
        String title = "Collection created!";
        String message = "Hello " + author + ", your new collection \"" + name + "\" has been created successfully!";
        return new Notification(null, author, title, message, Notification.NotificationStatus.UNREAD);
    }

    public Notification createCollectionUpdatedNotification() {
        validateFields();
        String title = "Collection updated!";
        String message = "Hello " + author + ", your collection \"" + name + "\" has been updated successfully!";
        return new Notification(null, author, title, message, Notification.NotificationStatus.UNREAD);
    }

    public Notification createCollectionDeletedNotification() {
        validateFields();
        String title = "Collection deleted!";
        String message = "Hello " + author + ", your collection \"" + name + "\" has been deleted successfully!";
        return new Notification(null, author, title, message, Notification.NotificationStatus.UNREAD);
    }

    private void validateFields() {
        if (author == null || name == null) {
            throw new IllegalArgumentException("The message does not contain the necessary fields");
        }
    }
}
