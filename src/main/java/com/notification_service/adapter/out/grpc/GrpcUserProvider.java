package com.notification_service.adapter.out.grpc;

import com.notification_service.domain.models.User;
import com.notification_service.ports.out.UserProvider;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import user.UserOuterClass;
import user.UserServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrpcUserProvider implements UserProvider {

    private static final Logger logger = LoggerFactory.getLogger(GrpcUserProvider.class);

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    @Override
    public List<User> listUsers() {
        UserOuterClass.Empty request = UserOuterClass.Empty.newBuilder().build();
        try {
            UserOuterClass.UserListResponse response = userServiceStub.listUsers(request);
            return response.getUsersList().stream()
                    .map(user -> {
                        User domainUser = new User();
                        domainUser.setUsername(user.getUsername());
                        return domainUser;
                    })
                    .collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            logger.error("Failed to connect to user-service: {}", e.getMessage());
            return List.of();
        }
    }
}