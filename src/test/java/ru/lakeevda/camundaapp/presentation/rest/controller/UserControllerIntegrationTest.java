package ru.lakeevda.camundaapp.presentation.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.port.in.usecase.UserUseCase;
import ru.lakeevda.camundaapp.presentation.dto.UserControllerCreateRequest;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    private static final String USERS_URL = "/api/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserUseCase userUseCase;

    @Test
    void create_user_returns201WithCreatedUser() throws Exception {
        // Given — валидные данные
        var request = new UserControllerCreateRequest(
                "Ivan Ivanov",
                LocalDate.of(1990, 1, 1),
                "ivan_" + UUID.randomUUID() + "@example.com"
        );
        String requestJson = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(post(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fio").value("Ivan Ivanov"))
                .andExpect(jsonPath("$.email").value(request.email()));
    }

    @Test
    void create_user_withMissingFields_returns400() throws Exception {
        // Given — пустой JSON, все обязательные поля отсутствуют
        String request = "{}";

        // When & Then
        mockMvc.perform(post(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_user_withInvalidEmail_returns400() throws Exception {
        // Given — email короче 5 символов (нарушение @Size)
        var request = new UserControllerCreateRequest(
                "Ivan Ivanov",
                LocalDate.of(1990, 1, 1),
                "ab"
        );
        String requestJson = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(post(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_user_withInvalidBirthday_returns400() throws Exception {
        // Given — дата рождения в будущем (нарушение @Past)
        var request = new UserControllerCreateRequest(
                "Petr Petrov",
                LocalDate.now().plusDays(1),
                "petr@example.com"
        );
        String requestJson = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(post(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByEmail_returns200WithUser() throws Exception {
        // Given — пользователь создан в БД через реальный UseCase
        String email = "test_" + UUID.randomUUID() + "@example.com";
        var createRequest = new UserUseCaseCreateRequest(
                "Test User",
                LocalDate.of(1985, 5, 5),
                email
        );
        userUseCase.create(createRequest);

        // When & Then
        mockMvc.perform(get(USERS_URL)
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fio").value("Test User"))
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void getByEmail_returns404WhenUserNotFound() throws Exception {
        // Given — пользователь с таким email не существует
        String email = "nonexistent_" + UUID.randomUUID() + "@example.com";

        // When & Then
        mockMvc.perform(get(USERS_URL)
                        .param("email", email))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByEmail_withEmptyEmail_returns400() throws Exception {
        // Given — пустой параметр email

        // When & Then
        mockMvc.perform(get(USERS_URL)
                        .param("email", ""))
                .andExpect(status().isBadRequest());
    }
}
