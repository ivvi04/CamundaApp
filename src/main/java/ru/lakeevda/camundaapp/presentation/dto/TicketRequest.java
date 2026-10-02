package ru.lakeevda.camundaapp.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequest(
        @NotNull(message = "name is required")
        String name,

        @NotBlank(message = "userId is required")
        @Min(value = 1, message = "userId must be positive")
        Long userId) {

}
