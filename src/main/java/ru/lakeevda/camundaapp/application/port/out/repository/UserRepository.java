package ru.lakeevda.camundaapp.application.port.out.repository;

import ru.lakeevda.camundaapp.domain.entity.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

    User save(User user);
}
