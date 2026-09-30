package ru.lakeevdan.camundaapp.application.dto;

import java.time.LocalDateTime;

public record TicketParamResponse(Long id,
                                  String name,
                                  LocalDateTime createAt,
                                  String status) {
}
