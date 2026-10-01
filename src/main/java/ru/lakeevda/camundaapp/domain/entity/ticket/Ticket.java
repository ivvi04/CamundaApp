package ru.lakeevda.camundaapp.domain.entity.ticket;

import lombok.Getter;
import ru.lakeevda.camundaapp.domain.entity.user.User;

/**
 * Domain entity representing a ticket.
 */
@Getter
public class Ticket {
    private TicketId id;
    private final TicketName name;
    private final TicketCreateAt createAt;
    private final TicketStatus status;
    private final User user;

    private Ticket(TicketName name, TicketCreateAt createAt, TicketStatus status, User user) {
        this.name = name;
        this.createAt = createAt;
        this.status = status;
        this.user = user;
    }

    private Ticket(TicketId id, TicketName name, TicketCreateAt createAt, TicketStatus status, User user) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.status = status;
        this.user = user;
    }

    public static Ticket create(TicketName name, TicketCreateAt createAt, TicketStatus status, User user) {
        if (name == null || createAt == null || status == null || user == null) {
            throw new IllegalArgumentException("Ticket name or createAt or status or user are null");
        }

        return new Ticket(name, createAt, status, user);
    }

    public static Ticket restore(TicketId id, TicketName name, TicketCreateAt createAt, TicketStatus status, User user) {
        if (id == null || name == null || createAt == null || status == null || user == null) {
            throw new IllegalArgumentException("Ticket id or name or createAt or status or user are null");
        }

        return new Ticket(id, name, createAt, status, user);
    }
}
