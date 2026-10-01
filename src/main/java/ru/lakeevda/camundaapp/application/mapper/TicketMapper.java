package ru.lakeevda.camundaapp.application.mapper;

import ru.lakeevda.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevda.camundaapp.application.dto.TicketParamResponse;
import ru.lakeevda.camundaapp.domain.entity.ticket.Ticket;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketCreateAt;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketName;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketStatus;
import ru.lakeevda.camundaapp.domain.entity.user.User;

public class TicketMapper {

    public static Ticket toDomain(TicketParamRequest param, User user) {
        return Ticket.create(
                TicketName.of(param.name()),
                TicketCreateAt.of(param.createAt()),
                TicketStatus.fromValue(param.status()),
                user
        );
    }

    public static TicketParamResponse fromDomain(Ticket domain) {
        return new TicketParamResponse(
                domain.getId().getValue(),
                domain.getName().getValue(),
                domain.getCreateAt().getValue(),
                domain.getStatus().getValue(),
                domain.getUser().getId().getValue());
    }
}
