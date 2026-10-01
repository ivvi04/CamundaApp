package ru.lakeevda.camundaapp.application.dto;

import java.time.LocalDate;

public record UserParamRequest(String fio, LocalDate birthday, String email) {
}
