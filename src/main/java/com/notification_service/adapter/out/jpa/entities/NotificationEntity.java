package com.notification_service.adapter.out.jpa.entities;

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

    public NotificationEntity(String username, String title, String message) {
        this.username = username;
        this.title = title;
        this.message = message;
    }
}