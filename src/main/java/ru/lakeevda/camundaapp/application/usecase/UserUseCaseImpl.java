package ru.lakeevda.camundaapp.application.usecase;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lakeevda.camundaapp.application.dto.UserParamRequest;
import ru.lakeevda.camundaapp.application.dto.UserParamResponse;
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
    public Long getIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(EntityNotFoundException::new)
                .getValue();
    }

    @Override
    public UserParamResponse create(UserParamRequest param) {
        User user = User.create(
                UserFio.of(param.fio()),
                UserBirthday.of(param.birthday()),
                UserEmail.of(param.email()));

        return UserMapper.fromDomain(userRepository.save(user));
    }
}
