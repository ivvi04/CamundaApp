package ru.lakeevda.camundaapp.infrastructure.adapter.camunda.worker;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import io.camunda.client.annotation.VariablesAsType;
import io.camunda.client.exception.BpmnError;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.domain.model.ticket.TicketStatus;
import ru.lakeevda.camundaapp.infrastructure.dto.camunda.ticket.TicketWorkerCreateRequest;
import ru.lakeevda.camundaapp.infrastructure.dto.camunda.ticket.TicketWorkerCreateResponse;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketWorker {

    private static final long MILLIS = 60001L;

    private final TicketUseCase useCase;

    @JobWorker(type = "ticketCreateJob")
    public TicketWorkerCreateResponse create(@VariablesAsType TicketWorkerCreateRequest request) {
        log.info("ticketCreateJob {} {}", request.ticketName(), request.userId());
        TicketUseCaseCreateRequest paramRequest = new TicketUseCaseCreateRequest(
                request.ticketName(),
                LocalDateTime.now(),
                TicketStatus.CREATED.getValue(),
                request.userId());
        try {
            TicketUseCaseCreateResponse paramResponse = useCase.create(paramRequest);
            return new TicketWorkerCreateResponse(paramResponse.id());
        } catch (EntityNotFoundException e) {
            log.warn("Throwing BpmnError BUSINESS_ERROR: {}", e.getMessage());
            throw new BpmnError("BUSINESS_ERROR", e.getMessage());
        }
    }

    @JobWorker(type = "ticketCheckJob")
    public void check(@Variable("ticketId") String ticketId) throws InterruptedException {
        log.info("ticketCheckJob {}", ticketId);
        if (Integer.parseInt(ticketId) % 2 == 0) {
            log.info("ticketCheckJob {} sleep", ticketId);
            Thread.sleep(MILLIS);
        }
    }
}
