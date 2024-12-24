/*package com.notification_service;

import com.notification_service.adapter.in.api.NSController;
import com.notification_service.domain.NSService;
import com.notification_service.domain.models.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Mockito für Unit-Tests, Mockmvc für Integrationstests (HTTP-Schnittstellen)

@WebMvcTest(NSController.class)
class NSControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NSService nsService;

    @Test
    void testGetNotifications_ValidRequest() throws Exception {
        // Arrange
        String username = "Testuser1";
        List<Notification> mockNotifications = List.of(
                new Notification(1L, "Testuser1", "Hallo", "Willkommen auf unserer Plattform!"),
                new Notification(2L, "Testuser1", "Erinnerung", "Vergiss nicht, ein Rezept auszuprobieren!")
        );
        when(nsService.getNotifications(username)).thenReturn(mockNotifications);

        // Act & Assert
        mockMvc.perform(get("/notifications")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].user").value("Testuser1"))
                .andExpect(jsonPath("$[0].title").value("Hallo"))
                .andExpect(jsonPath("$[0].message").value("Willkommen auf unserer Plattform!"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Erinnerung"));

        verify(nsService, times(1)).getNotifications(username);
    }

    @Test
    void testGetNotifications_EmptyResponse() throws Exception {
        // Arrange
        String username = "Testuser2";
        when(nsService.getNotifications(username)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/notifications")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(nsService, times(1)).getNotifications(username);
    }

    @Test
    void testGetNotifications_MissingParameter() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/notifications"))
                .andExpect(status().isBadRequest());
    }
}

    */