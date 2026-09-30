package ru.lakeevdan.camundaapp.domain.entity.user;

import lombok.Getter;

/**
 * Value object representing a full name.
 */
@Getter
public final class UserFio {
    private final String value;

    private UserFio(String value) {
        this.value = value;
    }

    public static UserFio of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name must not be empty");
        }
        return new UserFio(value.trim());
    }
}
