package ru.lakeevda.camundaapp.application.port.in.usecase;

import ru.lakeevda.camundaapp.application.dto.UserParamRequest;
import ru.lakeevda.camundaapp.application.dto.UserParamResponse;

public interface UserUseCase {
    UserParamResponse getByEmail(String email);
    UserParamResponse create(UserParamRequest param);
}
