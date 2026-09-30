package ru.lakeevdan.camundaapp.infrastructure.persistence.mapper;

import ru.lakeevdan.camundaapp.domain.entity.ticket.Ticket;
import ru.lakeevdan.camundaapp.domain.entity.ticket.TicketName;
import ru.lakeevdan.camundaapp.infrastructure.persistence.entity.TicketEntity;

public class TicketMapper {
    public static TicketEntity toEntity(Ticket domain) {
        TicketEntity entity = new TicketEntity();
        if (domain.getId() != null) entity.setId(domain.getId());
        entity.setName(domain.getName().getValue());
        entity.setCreateAt(domain.getCreateAt());
        entity.setStatus(domain.getStatus());

        return entity;
    }

    public static Ticket fromEntity(TicketEntity entity) {
        return Ticket.restore(entity.getId(),
                TicketName.of(entity.getName()),
                entity.getCreateAt(),
                entity.getStatus(),
                UserMapper.fromEntity(entity.getUser()));
    }
}
