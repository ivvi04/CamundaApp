package ru.lakeevda.camundaapp.domain.model.user;

/**
 * Value object representing an email address.
 */
public final class UserEmail {
    private final String value;

    private UserEmail(String value) {
        this.value = value;
    }

    public static UserEmail of(String value) {
        if (value == null || !value.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return new UserEmail(value.trim());
    }

    public String getValue() {
        return this.value;
    }
}
