package ru.lakeevda.camundaapp.application.port.in.usecase;

import ru.lakeevda.camundaapp.application.dto.TicketCreateUseCaseRequest;
import ru.lakeevda.camundaapp.application.dto.TicketCreateUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.TicketGetUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.TicketStartProcessUseCaseResponse;

import java.util.List;

public interface TicketUseCase {
    List<TicketGetUseCaseResponse> getByUserId(Long userId);

    TicketCreateUseCaseResponse create(TicketCreateUseCaseRequest param);
    void delete(Long ticketId);

    TicketStartProcessUseCaseResponse startProcess(Long userId);
}
