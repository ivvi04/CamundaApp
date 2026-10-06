package ru.lakeevda.camundaapp.presentation.mapper;

import ru.lakeevda.camundaapp.application.dto.UserCreateUseCaseRequest;
import ru.lakeevda.camundaapp.application.dto.UserCreateUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.UserGetUseCaseResponse;
import ru.lakeevda.camundaapp.presentation.dto.UserCreateControllerRequest;
import ru.lakeevda.camundaapp.presentation.dto.UserCreateControllerResponse;
import ru.lakeevda.camundaapp.presentation.dto.UserGetControllerResponse;

public class UserMapper {

    public static UserGetControllerResponse toGetResponse(UserGetUseCaseResponse paramResponse) {
        return new UserGetControllerResponse(
                paramResponse.id(), paramResponse.fio(), paramResponse.birthday(), paramResponse.email());
    }

    public static UserCreateUseCaseRequest toCreateRequest(UserCreateControllerRequest userRequest) {
        return new UserCreateUseCaseRequest(userRequest.fio(), userRequest.birthday(), userRequest.email());
    }

    public static UserCreateControllerResponse toCreateResponse(UserCreateUseCaseResponse paramResponse) {
        return new UserCreateControllerResponse(
                paramResponse.id(), paramResponse.fio(), paramResponse.birthday(), paramResponse.email());
    }
}
