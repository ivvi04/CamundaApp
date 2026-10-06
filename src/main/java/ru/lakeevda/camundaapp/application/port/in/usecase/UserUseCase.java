package ru.lakeevda.camundaapp.application.port.in.usecase;

import ru.lakeevda.camundaapp.application.dto.UserCreateUseCaseRequest;
import ru.lakeevda.camundaapp.application.dto.UserCreateUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.UserGetUseCaseResponse;

public interface UserUseCase {
    UserGetUseCaseResponse getByEmail(String email);

    UserCreateUseCaseResponse create(UserCreateUseCaseRequest param);
}
