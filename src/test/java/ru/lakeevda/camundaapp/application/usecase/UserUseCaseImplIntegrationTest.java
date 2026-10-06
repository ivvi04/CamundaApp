package ru.lakeevda.camundaapp.application.usecase;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseGetResponse;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserUseCaseImplIntegrationTest {

    @Autowired
    private UserUseCaseImpl userUseCase;

    @Test
    void getIdByEmail_returnsUserId() {
        // Given
        String email = "ivan@example.com";
        UserUseCaseCreateRequest paramRequest = new UserUseCaseCreateRequest("Ivan Ivanov", LocalDate.of(1990, 1, 1), "ivan@example.com");

        // When
        UserUseCaseCreateResponse createParamResponse = userUseCase.create(paramRequest);
        UserUseCaseGetResponse getParamResponse = userUseCase.getByEmail(email);

        // Then
        assertNotNull(createParamResponse);
        assertNotNull(getParamResponse);
        assertEquals(createParamResponse.id(), getParamResponse.id());
    }

    @Test
    void getIdByEmail_throwsWhenNotFound() {
        // Given
        String email = "unknown@example.com";

        // When / Then
        assertThrows(EntityNotFoundException.class, () -> userUseCase.getByEmail(email));
    }

    @Test
    void create_savesUser() {
        // Given
        UserUseCaseCreateRequest paramRequest = new UserUseCaseCreateRequest("Ivan Ivanov", LocalDate.of(1990, 1, 1), "ivan@example.com");

        // When
        UserUseCaseCreateResponse paramResponse = userUseCase.create(paramRequest);

        // Then
        assertNotNull(paramResponse);
        assertNotNull(paramResponse.id());
        assertEquals(paramRequest.fio(), paramResponse.fio());
        assertEquals(paramRequest.birthday(), paramResponse.birthday());
        assertEquals(paramRequest.email(), paramResponse.email());
    }

    @Test
    void create_throwsWhenFioIsNull() {
        // Given
        UserUseCaseCreateRequest paramRequest = new UserUseCaseCreateRequest(null, LocalDate.of(1990, 1, 1), "ivan@example.com");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userUseCase.create(paramRequest));
    }

    @Test
    void create_throwsWhenBirthdayIsNull() {
        // Given
        UserUseCaseCreateRequest paramRequest = new UserUseCaseCreateRequest("Ivan Ivanov", null, "ivan@example.com");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userUseCase.create(paramRequest));
    }

    @Test
    void create_throwsWhenEmailIsNull() {
        // Given
        UserUseCaseCreateRequest paramRequest = new UserUseCaseCreateRequest("Ivan Ivanov", LocalDate.of(1990, 1, 1), null);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userUseCase.create(paramRequest));
    }
}
