package ru.lakeevdan.camundaapp.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lakeevdan.camundaapp.application.port.out.repository.TicketRepository;
import ru.lakeevdan.camundaapp.domain.entity.ticket.Ticket;
import ru.lakeevdan.camundaapp.infrastructure.persistence.mapper.TicketMapper;
import ru.lakeevdan.camundaapp.infrastructure.persistence.repository.TicketJpaRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {
    private final TicketJpaRepository jpaRepository;

    @Override
    public List<Ticket> findByUserId(Long userId) {
        return jpaRepository.findAllByUserId(userId)
                .stream().map(TicketMapper::fromEntity)
                .toList();
    }

    @Override
    public void save(Ticket ticket) {
        jpaRepository.save(TicketMapper.toEntity(ticket));
    }
}
