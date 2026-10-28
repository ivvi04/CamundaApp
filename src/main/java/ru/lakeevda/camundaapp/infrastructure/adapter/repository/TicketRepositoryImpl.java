package ru.lakeevda.camundaapp.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lakeevda.camundaapp.application.port.out.repository.TicketRepository;
import ru.lakeevda.camundaapp.domain.model.ticket.Ticket;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.TicketEntity;
import ru.lakeevda.camundaapp.infrastructure.persistence.mapper.TicketMapper;
import ru.lakeevda.camundaapp.infrastructure.persistence.repository.TicketJpaRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {
    private final TicketJpaRepository jpaRepository;

    /**
     * Маппинг выполняется внутри транзакции, поэтому ленивая связь user
     * доступна без Open Session in View. Дополнительно связь загружается
     * через EntityGraph в репозитории, чтобы не было N+1.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findByUserId(Long userId) {
        return jpaRepository.findAllByUserId(userId)
                .stream().map(TicketMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Ticket save(Ticket ticket) {
        TicketEntity entity = jpaRepository.save(TicketMapper.toEntity(ticket));
        return TicketMapper.toDomain(entity);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}
