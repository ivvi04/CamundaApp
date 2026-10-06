package ru.lakeevda.camundaapp.infrastructure.persistence.mapper;

import ru.lakeevda.camundaapp.domain.entity.ticket.*;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.TicketEntity;

public class TicketMapper {
    public static TicketEntity toEntity(Ticket domain) {
        TicketEntity entity = new TicketEntity();
        if (domain.getId() != null) entity.setId(domain.getId().getValue());
        entity.setName(domain.getName().getValue());
        entity.setCreateAt(domain.getCreateAt().getValue());
        entity.setStatus(domain.getStatus().getValue());
        entity.setUser(UserMapper.toEntity(domain.getUser()));

        return entity;
    }

    public static Ticket toDomain(TicketEntity entity) {
        return Ticket.restore(TicketId.of(entity.getId()),
                TicketName.of(entity.getName()),
                TicketCreateAt.of(entity.getCreateAt()),
                TicketStatus.fromValue(entity.getStatus()),
                UserMapper.toDomain(entity.getUser()));
    }
}
