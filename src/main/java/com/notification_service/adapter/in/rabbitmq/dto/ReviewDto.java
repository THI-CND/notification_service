package com.notification_service.adapter.in.rabbitmq.dto;

import com.notification_service.domain.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Long recipeId;
    private String author;
    private float rating;
    private String comment;


    public Notification createReviewCreatedNotification() {
        validateFields();
        String title = "Review created!";
        String message = "Hello " + author + ", your review for recipe " + recipeId + " has been created successfully!";
        return new Notification(null, author, title, message, Notification.NotificationStatus.UNREAD);
    }

    private void validateFields() {
        if (author == null || recipeId == null) {
            throw new IllegalArgumentException("The message does not contain the necessary fields");
        }
    }
}