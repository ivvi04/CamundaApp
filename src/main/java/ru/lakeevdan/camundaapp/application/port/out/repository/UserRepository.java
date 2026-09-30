package ru.lakeevdan.camundaapp.application.port.out.repository;

import ru.lakeevdan.camundaapp.domain.entity.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);
    void save(User user);
}
