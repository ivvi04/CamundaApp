package ru.lakeevda.camundaapp.application.usecase;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseGetResponse;
import ru.lakeevda.camundaapp.application.port.out.repository.UserRepository;
import ru.lakeevda.camundaapp.domain.model.user.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Замените на ваш реальный порт-интерфейс:

@ExtendWith(MockitoExtension.class)
class UserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    @Test
    void getIdByEmail_returnsUserId() {
        // Given — пользователь существует в репозитории
        String email = "ivan@example.com";
        var user = User.restore(
                UserId.of(1L),
                UserFio.of("Ivan Ivanov"),
                UserBirthday.of(LocalDate.of(1990, 1, 1)),
                UserEmail.of(email));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        UserUseCaseGetResponse response = userUseCase.getByEmail(email);

        // Then
        assertNotNull(response);
        assertEquals(user.getId().getValue(), response.id());

        verify(userRepository).findByEmail(email);
    }

    @Test
    void getIdByEmail_throwsWhenNotFound() {
        // Given — репозиторий не находит пользователя
        String email = "unknown@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(EntityNotFoundException.class, () -> userUseCase.getByEmail(email));

        verify(userRepository).findByEmail(email);
    }

    @Test
    void create_savesUser() {
        // Given — репозиторий возвращает сохранённого пользователя с ID
        var birthday = LocalDate.of(1990, 1, 1);
        var savedUser = User.restore(
                UserId.of(1L),
                UserFio.of("Ivan Ivanov"),
                UserBirthday.of(birthday),
                UserEmail.of("ivan@example.com"));

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        var request = new UserUseCaseCreateRequest("Ivan Ivanov", birthday, "ivan@example.com");

        // When
        UserUseCaseCreateResponse response = userUseCase.create(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals(request.fio(), response.fio());
        assertEquals(request.birthday(), response.birthday());
        assertEquals(request.email(), response.email());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void create_throwsWhenFioIsNull() {
        // Given — null fio вызовет IllegalArgumentException в UserFio.of()
        var request = new UserUseCaseCreateRequest(null, LocalDate.of(1990, 1, 1), "ivan@example.com");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userUseCase.create(request));

        verifyNoInteractions(userRepository);
    }

    @Test
    void create_throwsWhenBirthdayIsNull() {
        // Given — null birthday вызовет IllegalArgumentException в UserBirthday.of()
        var request = new UserUseCaseCreateRequest("Ivan Ivanov", null, "ivan@example.com");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userUseCase.create(request));

        verifyNoInteractions(userRepository);
    }

    @Test
    void create_throwsWhenEmailIsNull() {
        // Given — null email вызовет IllegalArgumentException в UserEmail.of()
        var request = new UserUseCaseCreateRequest("Ivan Ivanov", LocalDate.of(1990, 1, 1), null);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userUseCase.create(request));

        verifyNoInteractions(userRepository);
    }
}
