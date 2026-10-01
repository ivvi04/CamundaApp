package ru.lakeevda.camundaapp.domain.entity.ticket;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Value object representing a user's createAt.
 */
@Getter
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
}
