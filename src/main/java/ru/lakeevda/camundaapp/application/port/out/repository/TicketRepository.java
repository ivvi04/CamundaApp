package ru.lakeevda.camundaapp.application.port.out.repository;

import ru.lakeevda.camundaapp.domain.entity.ticket.Ticket;

import java.util.List;

public interface TicketRepository {
    List<Ticket> findByUserId(Long userId);

    Ticket save(Ticket ticket);
    void delete(Long id);
}
