package ru.lakeevda.camundaapp.application.dto;

import java.time.LocalDateTime;

public record TicketCreateUseCaseRequest(String name,
                                         LocalDateTime createAt,
                                         String status,
                                         Long userId) {
}
