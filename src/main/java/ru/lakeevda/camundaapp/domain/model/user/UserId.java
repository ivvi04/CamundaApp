package ru.lakeevda.camundaapp.domain.model.user;

/**
 * Value object representing a user identifier.
 */
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

    public Long getValue() {
        return this.value;
    }
}
