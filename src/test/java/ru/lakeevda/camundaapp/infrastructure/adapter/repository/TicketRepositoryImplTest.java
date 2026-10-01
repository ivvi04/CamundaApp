package ru.lakeevda.camundaapp.infrastructure.adapter.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lakeevda.camundaapp.domain.entity.ticket.*;
import ru.lakeevda.camundaapp.domain.entity.user.*;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.TicketEntity;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.UserEntity;
import ru.lakeevda.camundaapp.infrastructure.persistence.mapper.TicketMapper;
import ru.lakeevda.camundaapp.infrastructure.persistence.repository.TicketJpaRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketRepositoryImplTest {

    @Mock
    private TicketJpaRepository jpaRepository;
    @InjectMocks
    private TicketRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        reset(jpaRepository);
    }

    @Test
    void findByUserId_returnsMappedTickets() {
        // Given
        Long userId = 1L;
        List<TicketEntity> entities = List.of(
                createTicketEntity(1L, "Bug fix", LocalDateTime.now(), "resolved"),
                createTicketEntity(2L, "Feature", LocalDateTime.now().minusDays(1), "created")
        );
        when(jpaRepository.findAllByUserId(userId)).thenReturn(entities);

        // When
        List<Ticket> result = repository.findByUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Bug fix", result.get(0).getName().getValue());
        assertEquals("Feature", result.get(1).getName().getValue());
        verify(jpaRepository).findAllByUserId(userId);
    }

    @Test
    void findByUserId_returnsEmptyList() {
        // Given
        when(jpaRepository.findAllByUserId(anyLong())).thenReturn(List.of());

        // When
        List<Ticket> result = repository.findByUserId(999L);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_returnsEntityId() {
        // Given
        Ticket ticket = Ticket.restore(TicketId.of(10L),
                TicketName.of("Task"),
                TicketCreateAt.of(LocalDateTime.now()),
                TicketStatus.CREATED,
                User.restore(UserId.of(1L),
                        UserFio.of("Ivan Ivanov"),
                        UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)),
                        UserEmail.of("ivan@example.com")));

        when(jpaRepository.save(any(TicketEntity.class))).thenReturn(TicketMapper.toEntity(ticket));

        // When
        Ticket result = repository.save(ticket);

        // Then
        assertEquals(ticket.getId().getValue(), result.getId().getValue());
        verify(jpaRepository).save(any(TicketEntity.class));
    }

    @Test
    void delete_delegatesToJpaRepository() {
        // Given
        Long id = 100L;

        // When
        repository.delete(id);

        // Then
        verify(jpaRepository).deleteById(id);
    }

    private TicketEntity createTicketEntity(Long id, String name, LocalDateTime createAt, String status) {
        TicketEntity entity = new TicketEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setCreateAt(createAt);
        entity.setStatus(status);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFio("Ivan Ivanov");
        userEntity.setBirthday(java.time.LocalDate.of(1990, 1, 1));
        userEntity.setEmail("ivan@example.com");
        entity.setUser(userEntity);
        return entity;
    }
}
