package ru.lakeevda.camundaapp.domain.entity.user;

import lombok.Getter;

/**
 * Value object representing a user identifier.
 */
@Getter
public final class UserId {
    private final Long value;

    private UserId(Long value) {
        this.value = value;
    }

    public static UserId of(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("User id must be positive");
        }
        return new UserId(value);
    }
}
