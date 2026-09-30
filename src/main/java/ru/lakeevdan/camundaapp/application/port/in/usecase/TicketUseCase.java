package ru.lakeevdan.camundaapp.application.port.in.usecase;

import ru.lakeevdan.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevdan.camundaapp.application.dto.TicketParamResponse;

import java.util.List;

public interface TicketUseCase {
    List<TicketParamResponse> getByUserId(Long userId);
    void create(TicketParamRequest param);
    void delete(Long ticketId);
}
