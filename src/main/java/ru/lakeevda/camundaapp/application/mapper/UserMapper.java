package ru.lakeevda.camundaapp.application.mapper;

import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseGetResponse;
import ru.lakeevda.camundaapp.domain.entity.user.User;
import ru.lakeevda.camundaapp.domain.entity.user.UserBirthday;
import ru.lakeevda.camundaapp.domain.entity.user.UserEmail;
import ru.lakeevda.camundaapp.domain.entity.user.UserFio;

public class UserMapper {

    public static User toDomain(UserUseCaseCreateRequest param) {
        return User.create(
                UserFio.of(param.fio()),
                UserBirthday.of(param.birthday()),
                UserEmail.of(param.email()));
    }

    public static UserUseCaseCreateResponse toCreateResponse(User domain) {
        return new UserUseCaseCreateResponse(
                domain.getId().getValue(),
                domain.getFio().getValue(),
                domain.getBirthday().getValue(),
                domain.getEmail().getValue());
    }

    public static UserUseCaseGetResponse toGetResponse(User domain) {
        return new UserUseCaseGetResponse(
                domain.getId().getValue(),
                domain.getFio().getValue(),
                domain.getBirthday().getValue(),
                domain.getEmail().getValue());
    }

}
