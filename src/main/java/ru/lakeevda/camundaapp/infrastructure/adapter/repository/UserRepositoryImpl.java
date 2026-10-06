package ru.lakeevda.camundaapp.infrastructure.adapter.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lakeevda.camundaapp.application.port.out.repository.UserRepository;
import ru.lakeevda.camundaapp.domain.entity.user.User;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.UserEntity;
import ru.lakeevda.camundaapp.infrastructure.persistence.mapper.UserMapper;
import ru.lakeevda.camundaapp.infrastructure.persistence.repository.UserJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return jpaRepository.findById(userId)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity userEntity = jpaRepository.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(userEntity);
    }
}
