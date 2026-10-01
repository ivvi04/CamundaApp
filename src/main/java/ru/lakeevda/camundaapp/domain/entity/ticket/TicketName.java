package ru.lakeevda.camundaapp.domain.entity.ticket;

import lombok.Getter;

/**
 * Value object representing a name.
 */
@Getter
public final class TicketName {
    private final String value;

    private TicketName(String value) {
        this.value = value;
    }

    public static TicketName of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        return new TicketName(value.trim());
    }
}
