package ru.lakeevda.camundaapp.application.dto;

import java.time.LocalDateTime;

public record TicketCreateUseCaseResponse(Long id,
                                          String name,
                                          LocalDateTime createAt,
                                          String status,
                                          Long userId) {
}
