package ru.lakeevda.camundaapp.infrastructure.dto.camunda.ticket;

public record TicketCreateRequest(String ticketName, Long userId) {
}
