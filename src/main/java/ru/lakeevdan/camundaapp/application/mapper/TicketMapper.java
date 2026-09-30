package ru.lakeevdan.camundaapp.application.mapper;

import ru.lakeevdan.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevdan.camundaapp.application.dto.TicketParamResponse;
import ru.lakeevdan.camundaapp.domain.entity.ticket.TicketName;
import ru.lakeevdan.camundaapp.domain.entity.ticket.Ticket;
import ru.lakeevdan.camundaapp.domain.entity.user.User;

public class TicketMapper {

    public static Ticket toDomain(TicketParamRequest param, User user) {
        return Ticket.create(
            TicketName.of(param.name()),
            param.createAt(),
            param.status(),
            user
        );
    }

    public static TicketParamResponse fromDomain(Ticket domain) {
        return new TicketParamResponse(domain.getId(),
                domain.getName().getValue(),
                domain.getCreateAt(),
                domain.getStatus());
    }
}
