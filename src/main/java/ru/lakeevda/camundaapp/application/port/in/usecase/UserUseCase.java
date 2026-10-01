package ru.lakeevda.camundaapp.application.port.in.usecase;

import ru.lakeevda.camundaapp.application.dto.UserParamRequest;
import ru.lakeevda.camundaapp.application.dto.UserParamResponse;

public interface UserUseCase {
    Long getIdByEmail(String email);

    UserParamResponse create(UserParamRequest param);
}
