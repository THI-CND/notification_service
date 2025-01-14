package com.notification_service.domain;

import com.notification_service.domain.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
}
