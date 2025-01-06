package com.notification_service.domain;

import com.notification_service.domain.models.Notification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationFactory {

    public Notification collectionCreatedNotification(Map<String, Object> payload) {
        String name = (String) payload.get("name");
        String username = (String) payload.get("author");

        String title = "Collection created!";
        String message = "Hello " + username + ", your new collection \"" + name + "\" has been created successfully!";
        return new Notification(null, username, title, message, Notification.NotificationStatus.UNREAD);
    }

    public Notification collectionUpdatedNotification(Map<String, Object> payload) {
        String name = (String) payload.get("name");
        String username = (String) payload.get("author");

        String title = "Collection updated!";
        String message = "Hello " + username + ", your collection \"" + name + "\" has been updated successfully!";
        return new Notification(null, username, title, message, Notification.NotificationStatus.UNREAD);
    }

    public Notification collectionDeletedNotification(Map<String, Object> payload) {
        String name = (String) payload.get("name");
        String username = (String) payload.get("author");

        String title = "Collection deleted!";
        String message = "Hello " + username + ", your collection \"" + name + "\" has been deleted successfully!";
        return new Notification(null, username, title, message, Notification.NotificationStatus.UNREAD);
    }

    public Notification reviewCreatedNotification(Map<String, Object> payload) {
        String username = (String) payload.get("author");

        String title = "Review created!";
        String message = "Hello " + username + ", your review has been created successfully!";
        return new Notification(null, username, title, message, Notification.NotificationStatus.UNREAD);
    }

    public Map<String, String > numberOfUsersNotification(Map<String, Object> payload) {
        int numberOfUsers = (int) payload.get("numberOfUsers");
        String title = "User count updated!";
        String message = "The number of users is now " + numberOfUsers + "!";

        Map<String, String> result = new HashMap<>();
        result.put("title", title);
        result.put("message", message);
        return result;
    }
}
