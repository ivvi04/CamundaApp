package ru.lakeevda.camundaapp.application.port.in.usecase;

import ru.lakeevda.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevda.camundaapp.application.dto.TicketParamResponse;

import java.util.List;

public interface TicketUseCase {
    List<TicketParamResponse> getByUserId(Long userId);

    TicketParamResponse create(TicketParamRequest param);
    void delete(Long ticketId);
}
