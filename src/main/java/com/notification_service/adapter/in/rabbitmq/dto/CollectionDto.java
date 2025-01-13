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
        String title = "Sammlung erstellt!";
        String message = "Hallo " + author + ", deine neue Sammlung \"" + name + "\" wurde erfolgreich erstellt!";
        return new Notification(null, author, title, message, Notification.NotificationStatus.UNREAD);
    }

    public Notification createCollectionUpdatedNotification() {
        validateFields();
        String title = "Sammlung aktualisiert!";
        String message = "Hallo " + author + ", deine Sammlung \"" + name + "\" wurde erfolgreich aktualisiert!";
        return new Notification(null, author, title, message, Notification.NotificationStatus.UNREAD);
    }

    public Notification createCollectionDeletedNotification() {
        validateFields();
        String title = "Sammlung gelöscht!";
        String message = "Hallo " + author + ", deine Sammlung \"" + name + "\" wurde erfolgreich gelöscht!";
        return new Notification(null, author, title, message, Notification.NotificationStatus.UNREAD);
    }

    private void validateFields() {
        if (author == null || name == null) {
            throw new IllegalArgumentException("The message does not contain the necessary fields");
        }
    }




}
