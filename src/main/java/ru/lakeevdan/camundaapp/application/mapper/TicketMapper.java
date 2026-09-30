package ru.lakeevdan.camundaapp.application.mapper;

import ru.lakeevdan.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevdan.camundaapp.domain.entity.ticket.Ticket;

public class TicketMapper {

    public static Ticket toDomain(TicketParamRequest param) {
        return Ticket.create();
    }
}
