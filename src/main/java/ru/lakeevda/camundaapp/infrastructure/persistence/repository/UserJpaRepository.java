package ru.lakeevda.camundaapp.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.UserEntity;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

}
