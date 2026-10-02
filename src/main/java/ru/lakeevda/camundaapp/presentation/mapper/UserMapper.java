package ru.lakeevda.camundaapp.presentation.mapper;

import ru.lakeevda.camundaapp.application.dto.UserParamRequest;
import ru.lakeevda.camundaapp.application.dto.UserParamResponse;
import ru.lakeevda.camundaapp.presentation.dto.UserRequest;
import ru.lakeevda.camundaapp.presentation.dto.UserResponse;

public class UserMapper {
    public static UserParamRequest toParam(UserRequest userRequest) {
        return new UserParamRequest(userRequest.fio(), userRequest.birthday(), userRequest.email());
    }

    public static UserResponse fromParam(UserParamResponse paramResponse) {
        return new UserResponse(paramResponse.id(), paramResponse.fio(), paramResponse.birthday(), paramResponse.email());
    }
}
