package ru.lakeevda.camundaapp.application.mapper;

import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseGetResponse;
import ru.lakeevda.camundaapp.domain.model.ticket.Ticket;
import ru.lakeevda.camundaapp.domain.model.ticket.TicketCreateAt;
import ru.lakeevda.camundaapp.domain.model.ticket.TicketName;
import ru.lakeevda.camundaapp.domain.model.ticket.TicketStatus;
import ru.lakeevda.camundaapp.domain.model.user.User;

public class TicketMapper {

    public static Ticket toDomain(TicketUseCaseCreateRequest param, User user) {
        return Ticket.create(
                TicketName.of(param.name()),
                TicketCreateAt.of(param.createAt()),
                TicketStatus.fromValue(param.status()),
                user
        );
    }

    public static TicketUseCaseCreateResponse toCreateResponse(Ticket domain) {
        return new TicketUseCaseCreateResponse(
                domain.getId().getValue(),
                domain.getName().getValue(),
                domain.getCreateAt().getValue(),
                domain.getStatus().getValue(),
                domain.getUser().getId().getValue());
    }

    public static TicketUseCaseGetResponse toGetResponse(Ticket domain) {
        return new TicketUseCaseGetResponse(
                domain.getId().getValue(),
                domain.getName().getValue(),
                domain.getCreateAt().getValue(),
                domain.getStatus().getValue(),
                domain.getUser().getId().getValue());
    }
}
