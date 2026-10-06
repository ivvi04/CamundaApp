package ru.lakeevda.camundaapp.presentation.dto;

public record TicketStartProcessControllerResponse(String processInstanceKey, String bpmnProcessId, String version) {
}
