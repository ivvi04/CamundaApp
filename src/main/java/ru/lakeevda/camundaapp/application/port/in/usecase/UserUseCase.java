package ru.lakeevda.camundaapp.application.port.in.usecase;

import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseGetResponse;

public interface UserUseCase {
    UserUseCaseGetResponse getByEmail(String email);

    UserUseCaseCreateResponse create(UserUseCaseCreateRequest param);
}
