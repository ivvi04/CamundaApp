package ru.lakeevda.camundaapp.application.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseGetResponse;
import ru.lakeevda.camundaapp.application.port.out.repository.TicketRepository;
import ru.lakeevda.camundaapp.application.port.out.repository.UserRepository;
import ru.lakeevda.camundaapp.domain.model.ticket.*;
import ru.lakeevda.camundaapp.domain.model.user.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketUseCaseImplTest {

    private static final String USER_FIO = "fio";
    private static final String USER_EMAIL = "email@mail.ru";
    private static final String TICKET_NAME = "name";

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketUseCaseImpl ticketUseCase;

    @Test
    void create_success() {
        // Given — пользователь существует, репозиторий тикетов вернёт сохранённый тикет с ID
        var user = User.restore(
                UserId.of(1L),
                UserFio.of(USER_FIO),
                UserBirthday.of(LocalDate.now().minusYears(20)),
                UserEmail.of(USER_EMAIL));

        var createAt = LocalDateTime.now();
        var request = new TicketUseCaseCreateRequest(
                TICKET_NAME,
                createAt,
                TicketStatus.CREATED.getValue(),
                user.getId().getValue());

        var savedTicket = Ticket.restore(
                TicketId.of(1L),
                TicketName.of(TICKET_NAME),
                TicketCreateAt.of(createAt),
                TicketStatus.fromValue("created"),
                user);

        when(userRepository.findById(request.userId())).thenReturn(Optional.of(user));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        // When
        TicketUseCaseCreateResponse response = ticketUseCase.create(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals(request.name(), response.name());
        assertEquals(request.createAt(), response.createAt());
        assertEquals(request.status(), response.status());
        assertEquals(request.userId(), response.userId());

        verify(userRepository).findById(request.userId());
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void getByUserId_returnEmptyList() {
        // Given — репозиторий возвращает пустой список для несуществующего userId
        when(ticketRepository.findByUserId(999L)).thenReturn(Collections.emptyList());

        // When
        List<TicketUseCaseGetResponse> responses = ticketUseCase.getByUserId(999L);

        // Then
        assertNotNull(responses);
        assertEquals(0, responses.size());

        verify(ticketRepository).findByUserId(999L);
    }

    @Test
    void delete_success() {
        // Given — тикет с ID 1 будет удалён
        Long ticketId = 1L;
        doNothing().when(ticketRepository).delete(ticketId);

        // When
        ticketUseCase.delete(ticketId);

        // Then
        verify(ticketRepository).delete(ticketId);
    }
}
