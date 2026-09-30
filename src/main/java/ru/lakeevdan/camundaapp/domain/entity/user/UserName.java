package ru.lakeevdan.camundaapp.domain.entity.user;

import lombok.Getter;

/**
 * Value object representing a full name.
 */
@Getter
public final class UserName {
    private final String value;

    private UserName(String value) {
        this.value = value;
    }

    public static UserName of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name must not be empty");
        }
        return new UserName(value.trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserName)) return false;
        UserName that = (UserName) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "UserName{" + "value='" + value + '\'' + '}';
    }
}
