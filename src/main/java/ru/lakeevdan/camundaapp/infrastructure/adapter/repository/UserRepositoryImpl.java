package ru.lakeevdan.camundaapp.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lakeevdan.camundaapp.application.port.out.repository.UserRepository;
import ru.lakeevdan.camundaapp.domain.entity.user.User;
import ru.lakeevdan.camundaapp.infrastructure.persistence.mapper.UserMapper;
import ru.lakeevdan.camundaapp.infrastructure.persistence.repository.UserJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return jpaRepository.findById(userId)
                .map(UserMapper::fromEntity);
    }

    @Override
    public void save(User user) {

    }
}
