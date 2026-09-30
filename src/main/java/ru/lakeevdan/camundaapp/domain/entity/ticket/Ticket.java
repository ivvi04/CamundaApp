package ru.lakeevdan.camundaapp.domain.entity.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.lakeevdan.camundaapp.domain.entity.user.User;
import ru.lakeevdan.camundaapp.domain.entity.user.UserName;

import java.time.LocalDateTime;

/**
 * Domain entity representing a ticket.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    private Long id;
    private TicketName name;
    private LocalDateTime createAt;
    private String status;
    private User user;
}
