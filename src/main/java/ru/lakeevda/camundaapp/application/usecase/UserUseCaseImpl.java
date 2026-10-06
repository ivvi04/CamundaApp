package ru.lakeevda.camundaapp.application.usecase;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lakeevda.camundaapp.application.dto.UserCreateUseCaseRequest;
import ru.lakeevda.camundaapp.application.dto.UserCreateUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.UserGetUseCaseResponse;
import ru.lakeevda.camundaapp.application.mapper.UserMapper;
import ru.lakeevda.camundaapp.application.port.in.usecase.UserUseCase;
import ru.lakeevda.camundaapp.application.port.out.repository.UserRepository;
import ru.lakeevda.camundaapp.domain.entity.user.User;
import ru.lakeevda.camundaapp.domain.entity.user.UserBirthday;
import ru.lakeevda.camundaapp.domain.entity.user.UserEmail;
import ru.lakeevda.camundaapp.domain.entity.user.UserFio;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;

    @Override
    public UserGetUseCaseResponse getByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("email is null");
        }
        return userRepository.findByEmail(email)
                .map(UserMapper::toGetResponse)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("user with email %s not found", email)));
    }

    @Override
    public UserCreateUseCaseResponse create(UserCreateUseCaseRequest param) {
        if (param == null) {
            throw new IllegalArgumentException("param is null");
        }
        User user = User.create(
                UserFio.of(param.fio()),
                UserBirthday.of(param.birthday()),
                UserEmail.of(param.email()));

        return UserMapper.toCreateResponse(userRepository.save(user));
    }
}
