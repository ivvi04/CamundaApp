package ru.lakeevda.camundaapp.domain.entity.user;

/**
 * Value object representing a full name.
 */
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

    public String getValue() {
        return this.value;
    }
}
