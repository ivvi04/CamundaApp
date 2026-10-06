package ru.lakeevda.camundaapp.presentation.dto;

import java.time.LocalDateTime;

public record TicketGetControllerResponse(Long id, String name, LocalDateTime createdAt, String status) {
}
