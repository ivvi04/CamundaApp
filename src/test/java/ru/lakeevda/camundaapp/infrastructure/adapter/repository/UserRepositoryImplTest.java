package ru.lakeevda.camundaapp.infrastructure.adapter.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lakeevda.camundaapp.domain.entity.user.*;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.UserEntity;
import ru.lakeevda.camundaapp.infrastructure.persistence.mapper.UserMapper;
import ru.lakeevda.camundaapp.infrastructure.persistence.repository.UserJpaRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository jpaRepository;
    @InjectMocks
    private UserRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        reset(jpaRepository);
    }

    @Test
    void findById_returnsUserWhenFound() {
        // Given
        Long userId = 1L;
        UserEntity entity = createValidUserEntity(1L, "Ivan Ivanov", LocalDate.of(1990, 1, 1), "ivan@example.com");
        when(jpaRepository.findById(userId)).thenReturn(Optional.of(entity));

        // When
        Optional<User> result = repository.findById(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Ivan Ivanov", result.get().getFio().getValue());
        verify(jpaRepository).findById(userId);
    }

    @Test
    void findById_returnsEmptyWhenNotFound() {
        // Given
        Long userId = 999L;
        when(jpaRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        Optional<User> result = repository.findById(userId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void save_success() {
        // Given
        User user = User.restore(UserId.of(1L),
                UserFio.of("Ivan Ivanov"),
                UserBirthday.of(LocalDate.of(1990, 1, 1)),
                UserEmail.of("ivan@example.com"));
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(UserMapper.toEntity(user));

        // When
        repository.save(user);

        // Then
        verify(jpaRepository).save(any(UserEntity.class));
    }

    private UserEntity createValidUserEntity(Long id, String fio, LocalDate birthday, String email) {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setFio(fio);
        entity.setBirthday(birthday);
        entity.setEmail(email);
        return entity;
    }
}
