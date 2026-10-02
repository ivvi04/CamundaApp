package ru.lakeevda.camundaapp.domain.entity.user;

import java.time.LocalDate;

/**
 * Value object representing a user's birthday.
 */
public final class UserBirthday {
    private final LocalDate value;

    private UserBirthday(LocalDate value) {
        this.value = value;
    }

    public static UserBirthday of(LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("Birthday must not be null");
        }
        return new UserBirthday(value);
    }

    public LocalDate getValue() {
        return this.value;
    }
}
