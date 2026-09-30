package ru.lakeevdan.camundaapp.domain.entity.ticket;

import lombok.Getter;
import ru.lakeevdan.camundaapp.domain.entity.user.User;

import java.time.LocalDateTime;

/**
 * Domain entity representing a ticket.
 */
@Getter
public class Ticket {
    private Long id;
    private final TicketName name;
    private final LocalDateTime createAt;
    private final String status;
    private final User user;

    private Ticket(TicketName name, LocalDateTime createAt, String status, User user) {
        this.name = name;
        this.createAt = createAt;
        this.status = status;
        this.user = user;
    }

    private Ticket(Long id, TicketName name, LocalDateTime createAt, String status, User user) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.status = status;
        this.user = user;
    }

    public static Ticket create(TicketName name, LocalDateTime createAt, String status, User user) {
        return new Ticket(name, createAt, status, user);
    }

    public static Ticket restore(Long id, TicketName name, LocalDateTime createAt, String status, User user) {
        return new Ticket(id, name, createAt, status, user);
    }
}
