package ru.lakeevda.camundaapp.application.port.out.repository;

import ru.lakeevda.camundaapp.domain.model.ticket.Ticket;

import java.util.List;

public interface TicketRepository {
    List<Ticket> findByUserId(Long userId);

    Ticket save(Ticket ticket);

    boolean existsById(Long id);

    void delete(Long id);
}
