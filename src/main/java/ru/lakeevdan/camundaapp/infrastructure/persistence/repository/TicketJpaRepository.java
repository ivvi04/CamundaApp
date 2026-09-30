package ru.lakeevdan.camundaapp.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lakeevdan.camundaapp.infrastructure.persistence.entity.TicketEntity;

import java.util.List;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, Long> {

    @Query(value = "select t from TicketEntity t " +
            "where t.user.id = :userId")
    List<TicketEntity> findAllByUserId(@Param("userId") Long userId);

}
