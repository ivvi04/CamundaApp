package ru.lakeevda.camundaapp.presentation.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.application.port.in.usecase.UserUseCase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerIntegrationTest {

    private static final String TICKETS_URL = "/api/tickets";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketUseCase ticketUseCase;

    @Autowired
    private UserUseCase userUseCase;

    private String email;
    private Long userId;

    @BeforeEach
    void setUp() {
        email = "test_" + UUID.randomUUID() + "@example.com";
        var userRequest = new UserUseCaseCreateRequest(
                "Test User",
                LocalDate.of(1990, 1, 1),
                email
        );
        var userResponse = userUseCase.create(userRequest);
        userId = userResponse.id();
    }

    @Test
    void getByUserId_returns200WithEmptyListWhenNoTickets() throws Exception {
        // Given — пользователь существует, тикетов у него нет

        // When & Then
        mockMvc.perform(get(TICKETS_URL)
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getByUserId_returns200WithTicketsList() throws Exception {
        // Given — у пользователя есть один тикет
        var ticketRequest = new TicketUseCaseCreateRequest(
                "Test Ticket",
                LocalDateTime.now(),
                "created",
                userId
        );
        ticketUseCase.create(ticketRequest);

        // When & Then
        mockMvc.perform(get(TICKETS_URL)
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Test Ticket"));
    }

    @Test
    void getByUserId_withInvalidId_returns200WithEmptyList() throws Exception {
        // Given — userId = 1, пользователя с таким ID не существует
        long invalidUserId = Long.MAX_VALUE;

        // When & Then
        mockMvc.perform(get(TICKETS_URL)
                        .param("userId", String.valueOf(invalidUserId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getByUserId_withNullParam_returns400() throws Exception {
        // Given — параметр userId отсутствует

        // When & Then
        mockMvc.perform(get(TICKETS_URL))
                .andExpect(status().isBadRequest());
    }
}
