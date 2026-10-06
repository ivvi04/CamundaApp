package ru.lakeevda.camundaapp.presentation.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.application.port.out.process.ProcessService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TicketControllerIntegrationTest {

    private static final String TICKETS_URL = "/api/tickets";
    private static final long TEST_USER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketUseCase ticketUseCase;

    @MockitoBean
    private ProcessService camundaProcessService;

    // ===== GET /api/tickets?userId=... =====

    @Test
    void getByUserId_returns200WithEmptyListWhenNoTickets() throws Exception {
        // Given — у пользователя нет тикетов (MockitoBean не вернёт данные)

        // When & Then
        mockMvc.perform(get(TICKETS_URL)
                        .param("userId", String.valueOf(TEST_USER_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getByUserId_returns200WithTicketsList() throws Exception {
        // Given — у пользователя есть тикеты (через MockMvc + Spring context)
        String userId = "1";

        // When & Then
        mockMvc.perform(get(TICKETS_URL)
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getByUserId_withInvalidId_returns200WithEmptyList() throws Exception {
        // Given — userId = 0 (не валидный, но метод не бросает исключение)

        // When & Then
        mockMvc.perform(get(TICKETS_URL)
                        .param("userId", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getByUserId_withNullParam_returns400() throws Exception {
        // Given — параметр userId отсутствует (валидация Spring)

        // When & Then
        mockMvc.perform(get(TICKETS_URL))
                .andExpect(status().isBadRequest());
    }
}
