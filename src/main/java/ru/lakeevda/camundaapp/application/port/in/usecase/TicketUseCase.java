package ru.lakeevda.camundaapp.application.port.in.usecase;

import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseGetResponse;

import java.util.List;

public interface TicketUseCase {
    List<TicketUseCaseGetResponse> getByUserId(Long userId);

    TicketUseCaseCreateResponse create(TicketUseCaseCreateRequest param);
    void delete(Long ticketId);
}
