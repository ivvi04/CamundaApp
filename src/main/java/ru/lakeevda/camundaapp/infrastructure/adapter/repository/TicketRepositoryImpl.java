package ru.lakeevda.camundaapp.infrastructure.adapter.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lakeevda.camundaapp.application.port.out.repository.TicketRepository;
import ru.lakeevda.camundaapp.domain.entity.ticket.Ticket;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.TicketEntity;
import ru.lakeevda.camundaapp.infrastructure.persistence.mapper.TicketMapper;
import ru.lakeevda.camundaapp.infrastructure.persistence.repository.TicketJpaRepository;

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
    @Transactional
    public Ticket save(Ticket ticket) {
        TicketEntity entity = jpaRepository.save(TicketMapper.toEntity(ticket));
        return TicketMapper.fromEntity(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}
