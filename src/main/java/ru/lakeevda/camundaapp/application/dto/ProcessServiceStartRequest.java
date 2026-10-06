package ru.lakeevda.camundaapp.application.dto;

import java.util.Map;

public record ProcessServiceStartRequest(String processId, Map<String, Object> variables) {
}
