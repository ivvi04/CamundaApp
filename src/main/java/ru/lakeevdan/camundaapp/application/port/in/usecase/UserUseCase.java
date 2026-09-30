package ru.lakeevdan.camundaapp.application.port.in.usecase;

import ru.lakeevdan.camundaapp.application.dto.UserParamRequest;

public interface UserUseCase {
    Long getIdByEmail(String email);
    void create(UserParamRequest param);
}
