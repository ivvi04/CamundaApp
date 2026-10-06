package ru.lakeevda.camundaapp.presentation.dto;

public record ProcessControllerStartResponse(
        String processInstanceKey, String bpmnProcessId, String version) {
}
