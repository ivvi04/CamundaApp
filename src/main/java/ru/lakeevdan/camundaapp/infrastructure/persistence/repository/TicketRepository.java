package ru.lakeevdan.camundaapp.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lakeevdan.camundaapp.infrastructure.persistence.entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

}
