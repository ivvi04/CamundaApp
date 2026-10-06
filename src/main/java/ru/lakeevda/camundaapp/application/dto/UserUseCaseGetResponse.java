package ru.lakeevda.camundaapp.application.dto;

import java.time.LocalDate;

public record UserUseCaseGetResponse(Long id, String fio, LocalDate birthday, String email) {
}
