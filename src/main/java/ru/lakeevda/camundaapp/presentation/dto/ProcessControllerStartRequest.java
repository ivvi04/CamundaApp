package ru.lakeevda.camundaapp.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record ProcessControllerStartRequest(
        @NotNull(message = "processId is required")
        String processId,

        @NotNull(message = "variables is required")
        Map<String, Object> variables) {

}
