package ru.lakeevda.camundaapp.application.dto;

public record TicketStartProcessUseCaseResponse(String processInstanceKey, String bpmnProcessId, String version) {
}
