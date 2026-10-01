package ru.lakeevda.camundaapp.application.dto;

import java.time.LocalDateTime;

public record TicketParamRequest(String name,
                                 LocalDateTime createAt,
                                 String status,
                                 Long userId) {
}
