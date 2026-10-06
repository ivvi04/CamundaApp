package ru.lakeevda.camundaapp.domain.entity.ticket;

import java.time.LocalDateTime;

/**
 * Value object representing a user's createAt.
 */
public final class TicketCreateAt {
    private final LocalDateTime value;

    private TicketCreateAt(LocalDateTime value) {
        this.value = value;
    }

    public static TicketCreateAt of(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("CreateAt must not be null");
        }
        return new TicketCreateAt(value);
    }

    public LocalDateTime getValue() {
        return this.value;
    }
}
