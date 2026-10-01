package ru.lakeevda.camundaapp.infrastructure.adapter.camunda.ticket;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import io.camunda.client.annotation.VariablesAsType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.lakeevda.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevda.camundaapp.application.dto.TicketParamResponse;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketStatus;
import ru.lakeevda.camundaapp.infrastructure.dto.camunda.ticket.TicketCreateRequest;
import ru.lakeevda.camundaapp.infrastructure.dto.camunda.ticket.TicketCreateResponse;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketCreateWorker {

    private final TicketUseCase useCase;

    @JobWorker(type = "ticketCreateJob")
    public TicketCreateResponse ticketCreate(@VariablesAsType TicketCreateRequest request) {
        log.info("ticketCreateJob {} {}", request.ticketName(), request.userId());
        TicketParamRequest paramRequest = new TicketParamRequest(
                request.ticketName(),
                LocalDateTime.now(),
                TicketStatus.CREATED.getValue(),
                request.userId());
        TicketParamResponse paramResponse = useCase.create(paramRequest);
        return new TicketCreateResponse(paramResponse.id());
    }
}
