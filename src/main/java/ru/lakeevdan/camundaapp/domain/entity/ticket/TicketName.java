package ru.lakeevdan.camundaapp.domain.entity.ticket;

import lombok.Getter;

/**
 * Value object representing a full name.
 */
@Getter
public final class TicketName {
    private final String value;

    private TicketName(String value) {
        this.value = value;
    }

    public static TicketName of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name must not be empty");
        }
        return new TicketName(value.trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketName)) return false;
        TicketName that = (TicketName) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "TicketName{" + "value='" + value + '\'' + '}';
    }
}
