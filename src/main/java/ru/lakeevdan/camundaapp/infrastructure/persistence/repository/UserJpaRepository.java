package ru.lakeevdan.camundaapp.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lakeevdan.camundaapp.infrastructure.persistence.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

}
