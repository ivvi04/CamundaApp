package ru.lakeevda.camundaapp.infrastructure.adapter.camunda.worker;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import io.camunda.client.annotation.VariablesAsType;
import io.camunda.client.exception.BpmnError;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.lakeevda.camundaapp.application.dto.TicketCreateUseCaseRequest;
import ru.lakeevda.camundaapp.application.dto.TicketCreateUseCaseResponse;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketStatus;
import ru.lakeevda.camundaapp.infrastructure.dto.camunda.ticket.TicketCreateWorkerRequest;
import ru.lakeevda.camundaapp.infrastructure.dto.camunda.ticket.TicketCreateWorkerResponse;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketWorker {

    private static final long MILLIS = 60001L;

    private final TicketUseCase useCase;

    @JobWorker(type = "ticketCreateJob")
    public TicketCreateWorkerResponse create(@VariablesAsType TicketCreateWorkerRequest request) {
        log.info("ticketCreateJob {} {}", request.ticketName(), request.userId());
        TicketCreateUseCaseRequest paramRequest = new TicketCreateUseCaseRequest(
                request.ticketName(),
                LocalDateTime.now(),
                TicketStatus.CREATED.getValue(),
                request.userId());
        try {
            TicketCreateUseCaseResponse paramResponse = useCase.create(paramRequest);
            return new TicketCreateWorkerResponse(paramResponse.id());
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
