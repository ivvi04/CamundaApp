package ru.lakeevda.camundaapp.application.usecase;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lakeevda.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevda.camundaapp.application.dto.TicketParamResponse;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketStatus;
import ru.lakeevda.camundaapp.domain.entity.user.User;
import ru.lakeevda.camundaapp.domain.entity.user.UserBirthday;
import ru.lakeevda.camundaapp.domain.entity.user.UserEmail;
import ru.lakeevda.camundaapp.domain.entity.user.UserFio;
import ru.lakeevda.camundaapp.infrastructure.adapter.repository.UserRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class TicketUseCaseImplIntegrationTest {
    private static final String USER_FIO = "fio";
    private static final String USER_EMAIL = "email@mail.ru";
    private static final String TICKET_NAME = "name";

    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private TicketUseCaseImpl ticketUseCase;

    @Test
    public void create_success() {
        // Given
        User user = userRepository.save(
                User.create(
                        UserFio.of(USER_FIO),
                        UserBirthday.of(LocalDate.now().minusYears(20)),
                        UserEmail.of(USER_EMAIL)));
        TicketParamRequest paramRequest = new TicketParamRequest(TICKET_NAME,
                LocalDateTime.now(), TicketStatus.CREATED.getValue(), user.getId().getValue());

        // When
        TicketParamResponse paramResponse = ticketUseCase.create(paramRequest);

        // Then
        assertNotNull(paramResponse);
        assertNotNull(paramResponse.id());
        assertEquals(paramRequest.name(), paramResponse.name());
        assertEquals(paramRequest.createAt(), paramResponse.createAt());
        assertEquals(paramRequest.status(), paramResponse.status());
        assertEquals(paramRequest.userId(), paramResponse.userId());
    }


    @Test
    public void findByUserId_returnEmptyList() {
        // Given

        // When
        List<TicketParamResponse> paramResponses = ticketUseCase.getByUserId(999L);

        // Then
        assertNotNull(paramResponses);
        assertEquals(0, paramResponses.size());
    }

    @Test
    public void delete_success() {
        // When & Then
        ticketUseCase.delete(1L);
    }
}