package com.notification_service.adapter.in.rabbitmq.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRmqGenericMessageDto {
    Long id;
    private String name;
    private String author;
    private String description;
    private List<Long> recipes;
    private Long recipeId;
    private Float rating;
    private String comment;
    private Long numberOfUsers;


    //TODO: Add more fields for user and update Comment field from review
}
