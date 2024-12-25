package com.notification_service.adapter.in.grpc;

import com.notification_service.adapter.in.grpc.dto.GrpcDto;
import com.notification_service.domain.NSService;
import com.notification_service.domain.models.Notification;
import com.notification_service.stubs.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class NSGrpcController extends NotificationServiceGrpc.NotificationServiceImplBase {

    private final NSService nsService;

    public NSGrpcController(NSService nsService) {
        this.nsService = nsService;
    }

    @Override
    public void getAllNotifications(GetAllNotificationsRequest request, StreamObserver<GetAllNotificationsResponse> responseObserver) {
        List<Notification> notifications = nsService.getNotifications(request.getUsername());
        responseObserver.onNext(GrpcDto.toGetAllNotificationsResponse(notifications));
        responseObserver.onCompleted();
    }

    @Override
    public void getNotificationById(GetNotificationByIdRequest request, StreamObserver<NotificationResponse> responseObserver) {
        handleNotificationResponse(request.getId(), request.getUsername(), responseObserver);
    }

    @Override
    public void updateNotificationStatus(UpdateNotificationStatusRequest request, StreamObserver<NotificationResponse> responseObserver) {
        var notificationOpt = nsService.updateNotificationStatus(request.getId(), Notification.NotificationStatus.valueOf(request.getStatus().name()));
        handleNotificationResponse(notificationOpt, request.getUsername(), responseObserver);
    }

    @Override
    public void getNotificationsByStatus(GetNotificationsByStatusRequest request, StreamObserver<GetAllNotificationsResponse> responseObserver) {
        var notifications = nsService.getNotificationsByStatus(
                request.getUsername(),
                Notification.NotificationStatus.valueOf(request.getStatus().name())
        );
        responseObserver.onNext(GrpcDto.toGetAllNotificationsResponse(notifications));
        responseObserver.onCompleted();
    }


    private void handleNotificationResponse(Long id, String username, StreamObserver<NotificationResponse> responseObserver) {
        var notificationOpt = nsService.getNotificationById(id);
        handleNotificationResponse(notificationOpt, username, responseObserver);
    }

    private void handleNotificationResponse(java.util.Optional<Notification> notificationOpt, String username, StreamObserver<NotificationResponse> responseObserver) {
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            if (notification.getUser().equals(username)) {
                responseObserver.onNext(GrpcDto.toNotificationResponse(notification));
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.PERMISSION_DENIED.withDescription("Access Denied").asException());
            }
        } else {
            responseObserver.onError(Status.NOT_FOUND.withDescription("Notification Not Found").asException());
        }
    }
}