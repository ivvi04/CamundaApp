package ru.lakeevdan.camundaapp.application.port.out.repository;

import ru.lakeevdan.camundaapp.domain.entity.ticket.Ticket;

import java.util.List;

public interface TicketRepository {
    List<Ticket> findByUserId(Long userId);
    void save(Ticket ticket);
}
