package ru.lakeevda.camundaapp.infrastructure.dto.camunda.ticket;

public record TicketWorkerCreateRequest(String ticketName, Long userId) {
}
