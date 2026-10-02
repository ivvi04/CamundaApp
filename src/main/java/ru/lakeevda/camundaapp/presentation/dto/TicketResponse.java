package ru.lakeevda.camundaapp.presentation.dto;

import java.time.LocalDateTime;

public record TicketResponse(Long id, String name, LocalDateTime createdAt, String status) {
}
