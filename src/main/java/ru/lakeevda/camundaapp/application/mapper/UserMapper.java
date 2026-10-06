package ru.lakeevda.camundaapp.application.mapper;

import ru.lakeevda.camundaapp.application.dto.UserCreateUseCaseRequest;
import ru.lakeevda.camundaapp.application.dto.UserCreateUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.UserGetUseCaseResponse;
import ru.lakeevda.camundaapp.domain.entity.user.User;
import ru.lakeevda.camundaapp.domain.entity.user.UserBirthday;
import ru.lakeevda.camundaapp.domain.entity.user.UserEmail;
import ru.lakeevda.camundaapp.domain.entity.user.UserFio;

public class UserMapper {

    public static User toDomain(UserCreateUseCaseRequest param) {
        return User.create(
                UserFio.of(param.fio()),
                UserBirthday.of(param.birthday()),
                UserEmail.of(param.email()));
    }

    public static UserCreateUseCaseResponse toCreateResponse(User domain) {
        return new UserCreateUseCaseResponse(
                domain.getId().getValue(),
                domain.getFio().getValue(),
                domain.getBirthday().getValue(),
                domain.getEmail().getValue());
    }

    public static UserGetUseCaseResponse toGetResponse(User domain) {
        return new UserGetUseCaseResponse(
                domain.getId().getValue(),
                domain.getFio().getValue(),
                domain.getBirthday().getValue(),
                domain.getEmail().getValue());
    }

}
