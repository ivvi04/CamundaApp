package ru.lakeevda.camundaapp.application.dto;

public record ProcessServiceStartResponse(String processInstanceKey, String bpmnProcessId, String version) {
}
