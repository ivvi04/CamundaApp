package ru.lakeevda.camundaapp.presentation.dto;

import java.time.LocalDate;

public record UserCreateControllerResponse(Long id, String fio, LocalDate birthday, String email) {
}
