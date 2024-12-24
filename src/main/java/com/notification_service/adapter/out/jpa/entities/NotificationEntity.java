package com.notification_service.adapter.out.jpa.entities;

import com.notification_service.domain.models.Notification;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@Table(name = "notifications")
@NoArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String title;
    private String message;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    public NotificationEntity(String username, String title, String message, NotificationStatus status) {
        this.username = username;
        this.title = title;
        this.message = message;
        this.status = status;
    }
    public enum NotificationStatus {
        READ, UNREAD, DELETED
    }

    public Notification toNotification() {
        return new Notification(getId(), getUsername(), getTitle(), getMessage(), Notification.NotificationStatus.valueOf(getStatus().name()));
    }
}