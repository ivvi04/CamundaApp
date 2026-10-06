package ru.lakeevda.camundaapp.infrastructure.dto.camunda.ticket;

public record TicketCreateWorkerRequest(String ticketName, Long userId) {
}
