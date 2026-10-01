package ru.lakeevda.camundaapp.infrastructure.adapter.camunda.ticket;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TicketCheckWorker {

    @JobWorker(type = "ticketCheckJob")
    public void ticketCheck(@Variable("ticketId") String ticketId) {
        log.info("ticketCheckJob {}", ticketId);
    }
}
