package ru.lakeevda.camundaapp.application.mapper;

import ru.lakeevda.camundaapp.application.dto.TicketCreateUseCaseRequest;
import ru.lakeevda.camundaapp.application.dto.TicketCreateUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.TicketGetUseCaseResponse;
import ru.lakeevda.camundaapp.domain.entity.ticket.Ticket;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketCreateAt;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketName;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketStatus;
import ru.lakeevda.camundaapp.domain.entity.user.User;

public class TicketMapper {

    public static Ticket toDomain(TicketCreateUseCaseRequest param, User user) {
        return Ticket.create(
                TicketName.of(param.name()),
                TicketCreateAt.of(param.createAt()),
                TicketStatus.fromValue(param.status()),
                user
        );
    }

    public static TicketCreateUseCaseResponse toCreateResponse(Ticket domain) {
        return new TicketCreateUseCaseResponse(
                domain.getId().getValue(),
                domain.getName().getValue(),
                domain.getCreateAt().getValue(),
                domain.getStatus().getValue(),
                domain.getUser().getId().getValue());
    }

    public static TicketGetUseCaseResponse toGetResponse(Ticket domain) {
        return new TicketGetUseCaseResponse(
                domain.getId().getValue(),
                domain.getName().getValue(),
                domain.getCreateAt().getValue(),
                domain.getStatus().getValue(),
                domain.getUser().getId().getValue());
    }
}
