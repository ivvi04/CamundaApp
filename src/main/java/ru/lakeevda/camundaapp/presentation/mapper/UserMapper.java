package ru.lakeevda.camundaapp.presentation.mapper;

import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseGetResponse;
import ru.lakeevda.camundaapp.presentation.dto.UserControllerCreateRequest;
import ru.lakeevda.camundaapp.presentation.dto.UserControllerCreateResponse;
import ru.lakeevda.camundaapp.presentation.dto.UserControllerGetResponse;

public class UserMapper {

    public static UserControllerGetResponse toGetResponse(UserUseCaseGetResponse paramResponse) {
        return new UserControllerGetResponse(
                paramResponse.id(), paramResponse.fio(), paramResponse.birthday(), paramResponse.email());
    }

    public static UserUseCaseCreateRequest toCreateRequest(UserControllerCreateRequest userRequest) {
        return new UserUseCaseCreateRequest(userRequest.fio(), userRequest.birthday(), userRequest.email());
    }

    public static UserControllerCreateResponse toCreateResponse(UserUseCaseCreateResponse paramResponse) {
        return new UserControllerCreateResponse(
                paramResponse.id(), paramResponse.fio(), paramResponse.birthday(), paramResponse.email());
    }
}
