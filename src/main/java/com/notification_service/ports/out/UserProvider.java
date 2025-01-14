package com.notification_service.ports.out;

import com.notification_service.domain.models.User;

import java.util.List;

public interface UserProvider {
    List<User> listUsers();
}
