/*package com.notification_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.notification_service.domain.models.Notification;
import com.notification_service.ports.outgoing.NotificationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.notification_service.Application.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificationRestControllerV1Tests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NotificationRepository notificationRepository;

    // Beispiel Notification-Objekt
    private static final String USERNAME = "testUser";
    private static final Notification testNotification = new Notification(
            1L, USERNAME, "Test Title", "Test Message", Notification.NotificationStatus.UNREAD
    );

    // Setup für die Tests (optional)
    @BeforeEach
    void setUp() {
        // Hier können initiale Mock-Setups für das Repository durchgeführt werden.
    }

    @Test
    @Order(1)
    void testGetAllNotifications_Empty() throws Exception {
        // Mock Repository Antwort für leere Benachrichtigungen
        when(notificationRepository.findByUser(USERNAME)).thenReturn(List.of());

        // API-Aufruf zum Abrufen von Benachrichtigungen
        mvc.perform(get("/api/v1/notifications")
                        .param("username", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty()); // Erwartung: leere Liste
    }

    @Test
    @Order(2)
    void testGetAllNotifications_Filled() throws Exception {
        // Mock Repository Antwort für Benachrichtigungen
        when(notificationRepository.findByUser(USERNAME)).thenReturn(List.of(testNotification));

        // API-Aufruf zum Abrufen von Benachrichtigungen
        mvc.perform(get("/api/v1/notifications")
                        .param("username", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].message").value("Test Message"))
                .andExpect(jsonPath("$[0].status").value("UNREAD"));
    }

    @Test
    @Order(3)
    void testGetNotificationById_NotFound() throws Exception {
        Long id = 1L;
        // Mock Repository Antwort für eine nicht gefundene Benachrichtigung
        when(notificationRepository.findById(id)).thenReturn(Optional.empty());

        // API-Aufruf für eine nicht gefundene Benachrichtigung
        mvc.perform(get("/api/v1/notifications/{id}", id)
                        .param("username", USERNAME))
                .andExpect(status().isNotFound()); // Erwartung: 404 Not Found
    }

    @Test
    @Order(4)
    void testGetNotificationById_Success() throws Exception {
        Long id = 1L;
        // Mock Repository Antwort für eine gefundene Benachrichtigung
        when(notificationRepository.findById(id)).thenReturn(Optional.of(testNotification));

        // API-Aufruf für eine gefundene Benachrichtigung
        mvc.perform(get("/api/v1/notifications/{id}", id)
                        .param("username", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.message").value("Test Message"))
                .andExpect(jsonPath("$.status").value("UNREAD"));
    }

    @Test
    @Order(5)
    void testGetNotificationById_Forbidden() throws Exception {
        Long id = 1L;
        // Mock Repository Antwort für eine Benachrichtigung von einem anderen Benutzer
        Notification differentUserNotification = new Notification(id, "anotherUser", "Test Title", "Test Message", Notification.NotificationStatus.UNREAD);
        when(notificationRepository.findById(id)).thenReturn(Optional.of(differentUserNotification));

        // API-Aufruf mit falschem Benutzer
        mvc.perform(get("/api/v1/notifications/{id}", id)
                        .param("username", USERNAME))
                .andExpect(status().isForbidden()); // Erwartung: 403 Forbidden
    }

    @Test
    @Order(6)
    void testGetAllNotificationsByStatus() throws Exception {
        Notification.NotificationStatus status = Notification.NotificationStatus.UNREAD;
        // Mock Repository Antwort für Benachrichtigungen mit einem bestimmten Status
        when(notificationRepository.findByUserAndStatus(USERNAME, status)).thenReturn(List.of(testNotification));

        // API-Aufruf für Benachrichtigungen mit bestimmtem Status
        mvc.perform(get("/api/v1/notifications/status/{status}", status)
                        .param("username", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].message").value("Test Message"))
                .andExpect(jsonPath("$[0].status").value("UNREAD"));
    }

    // Hilfsmethode, um ein Objekt in JSON-String zu konvertieren
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

 */