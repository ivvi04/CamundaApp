package ru.lakeevdan.camundaapp.domain.entity.user;

import lombok.Getter;

/**
 * Value object representing an email address.
 */
@Getter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEmail)) return false;
        UserEmail that = (UserEmail) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "UserEmail{" + "value='" + value + '\'' + '}';
    }
}
