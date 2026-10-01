package ru.lakeevda.camundaapp.application.usecase;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lakeevda.camundaapp.application.dto.UserParamRequest;
import ru.lakeevda.camundaapp.application.dto.UserParamResponse;

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
        UserParamRequest paramRequest = new UserParamRequest("Ivan Ivanov", LocalDate.of(1990, 1, 1), "ivan@example.com");

        // When
        UserParamResponse paramResponse = userUseCase.create(paramRequest);
        Long result = userUseCase.getIdByEmail(email);

        // Then
        assertEquals(paramResponse.id(), result);
    }

    @Test
    void getIdByEmail_throwsWhenNotFound() {
        // Given
        String email = "unknown@example.com";

        // When / Then
        assertThrows(EntityNotFoundException.class, () -> userUseCase.getIdByEmail(email));
    }

    @Test
    void create_savesUser() {
        // Given
        UserParamRequest paramRequest = new UserParamRequest("Ivan Ivanov", LocalDate.of(1990, 1, 1), "ivan@example.com");

        // When
        UserParamResponse paramResponse = userUseCase.create(paramRequest);

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
        UserParamRequest paramRequest = new UserParamRequest(null, LocalDate.of(1990, 1, 1), "ivan@example.com");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userUseCase.create(paramRequest));
    }

    @Test
    void create_throwsWhenBirthdayIsNull() {
        // Given
        UserParamRequest paramRequest = new UserParamRequest("Ivan Ivanov", null, "ivan@example.com");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userUseCase.create(paramRequest));
    }

    @Test
    void create_throwsWhenEmailIsNull() {
        // Given
        UserParamRequest paramRequest = new UserParamRequest("Ivan Ivanov", LocalDate.of(1990, 1, 1), null);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userUseCase.create(paramRequest));
    }
}
