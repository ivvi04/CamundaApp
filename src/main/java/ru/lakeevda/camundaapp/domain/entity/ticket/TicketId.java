package ru.lakeevda.camundaapp.domain.entity.ticket;

import lombok.Getter;

/**
 * Value object representing a ticket identifier.
 */
@Getter
public final class TicketId {
    private final Long value;

    private TicketId(Long value) {
        this.value = value;
    }

    public static TicketId of(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Ticket id must be positive");
        }
        return new TicketId(value);
    }
}
