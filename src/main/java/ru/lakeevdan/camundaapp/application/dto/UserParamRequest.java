package ru.lakeevdan.camundaapp.application.dto;

import java.time.LocalDate;

public record UserParamRequest (String fio, LocalDate birthday) {
}
