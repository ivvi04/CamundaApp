package ru.lakeevda.camundaapp.presentation.dto;

import java.time.LocalDate;

public record UserGetControllerResponse(Long id, String fio, LocalDate birthday, String email) {
}
