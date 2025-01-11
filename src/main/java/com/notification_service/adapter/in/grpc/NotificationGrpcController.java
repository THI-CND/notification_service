package com.notification_service.adapter.in.grpc;

import com.notification_service.adapter.in.grpc.grpcmapper.NotificationGrpcMapper;
import com.notification_service.domain.NotificationService;
import com.notification_service.domain.models.Notification;
import com.notification_service.stubs.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.Optional;

@GrpcService
public class NotificationGrpcController extends NotificationServiceGrpc.NotificationServiceImplBase {

    private final NotificationService notificationService;

    public NotificationGrpcController(NotificationService nsService) {
        this.notificationService = nsService;
    }

    @Override
    public void getAllNotifications(GetAllNotificationsRequest request, StreamObserver<GetAllNotificationsResponse> responseObserver) {
        List<Notification> notifications = notificationService.getNotifications(request.getUsername());
        responseObserver.onNext(NotificationGrpcMapper.toGetAllNotificationsResponse(notifications));
        responseObserver.onCompleted();
    }

    @Override
    public void getNotificationById(GetNotificationByIdRequest request, StreamObserver<NotificationResponse> responseObserver) {
        var notificationOpt = notificationService.getNotificationById(request.getId());
        handleNotificationResponse(notificationOpt, request.getUsername(), responseObserver);
    }

    @Override
    public void updateNotificationStatus(UpdateNotificationStatusRequest request, StreamObserver<NotificationResponse> responseObserver) {
        var notificationOpt = notificationService.updateNotificationStatus(request.getId(), Notification.NotificationStatus.valueOf(request.getStatus().name()));
        handleNotificationResponse(notificationOpt, request.getUsername(), responseObserver);
    }

    @Override
    public void getNotificationsByStatus(GetNotificationsByStatusRequest request, StreamObserver<GetAllNotificationsResponse> responseObserver) {
        var notifications = notificationService.getNotificationsByStatus(
                request.getUsername(),
                Notification.NotificationStatus.valueOf(request.getStatus().name())
        );
        responseObserver.onNext(NotificationGrpcMapper.toGetAllNotificationsResponse(notifications));
        responseObserver.onCompleted();
    }

    private void handleNotificationResponse(Optional<Notification> notificationOpt, String username, StreamObserver<NotificationResponse> responseObserver) {
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            if (notification.getUsername().equals(username)) {
                responseObserver.onNext(NotificationGrpcMapper.toNotificationResponse(notification));
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.PERMISSION_DENIED.withDescription("Access Denied").asException());
            }
        } else {
            responseObserver.onError(Status.NOT_FOUND.withDescription("Notification Not Found").asException());
        }
    }
}