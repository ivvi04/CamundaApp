package ru.lakeevda.camundaapp.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseGetResponse;
import ru.lakeevda.camundaapp.application.exception.NotFoundException;
import ru.lakeevda.camundaapp.application.mapper.UserMapper;
import ru.lakeevda.camundaapp.application.port.in.usecase.UserUseCase;
import ru.lakeevda.camundaapp.application.port.out.repository.UserRepository;
import ru.lakeevda.camundaapp.domain.model.user.User;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;

    @Override
    public UserUseCaseGetResponse getByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("email is null");
        }
        return userRepository.findByEmail(email)
                .map(UserMapper::toGetResponse)
                .orElseThrow(() ->
                        new NotFoundException(String.format("user with email %s not found", email)));
    }

    @Override
    public UserUseCaseCreateResponse create(UserUseCaseCreateRequest param) {
        if (param == null) {
            throw new IllegalArgumentException("param is null");
        }
        User user = UserMapper.toDomain(param);

        return UserMapper.toCreateResponse(userRepository.save(user));
    }
}
