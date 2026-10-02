package ru.lakeevda.camundaapp.presentation.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRequest(
        @NotNull(message = "fio is required")
        String fio,

        @NotNull(message = "birthday is required")
        @FutureOrPresent
        LocalDate birthday,

        @NotNull(message = "email is required")
        @Size(min = 5, max = 255, message = "email address must contain between 5 and 255 characters")
        String email) {
}
