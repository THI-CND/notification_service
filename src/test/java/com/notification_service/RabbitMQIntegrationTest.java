/*
Lombock funktioniert hier irgendwie nicht :(

package com.notification_service;

import com.notification_service.adapter.out.jpa.JpaNSRepository;
import com.notification_service.adapter.out.jpa.entities.NotificationEntity;
import com.notification_service.domain.models.Notification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.RabbitMQContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RabbitMQIntegrationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JpaNSRepository repository;

    @BeforeAll
    static void startContainer() {
        RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management")
                .withExposedPorts(5672, 15672);
        rabbitMQContainer.start();
        System.setProperty("spring.rabbitmq.host", rabbitMQContainer.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitMQContainer.getAmqpPort().toString());
    }

    @Test
    void testSendAndReceiveMessage() {
        Notification notification = new Notification(1L, "user1", "title", "message");

        rabbitTemplate.convertAndSend("collection_service_exchange", "collection.updated", notification);

        // Sleep, um Verarbeitung durch den Consumer zu ermöglichen
        Thread.sleep(2000); // Wartezeit für die Verarbeitung, falls asynchron

        // Assert: Überprüfen, ob die Nachricht in der Datenbank gespeichert wurde
        List<NotificationEntity> storedNotifications = repository.findByUsername("user1");

        assertThat(storedNotifications).hasSize(1);
        NotificationEntity storedNotification = storedNotifications.get(0);
        assertThat(storedNotification.getTitle()).isEqualTo("title");
        assertThat(storedNotification.getMessage()).isEqualTo("message");
    }
}

 */