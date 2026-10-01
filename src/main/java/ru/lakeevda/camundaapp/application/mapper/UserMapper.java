package ru.lakeevda.camundaapp.application.mapper;

import ru.lakeevda.camundaapp.application.dto.UserParamRequest;
import ru.lakeevda.camundaapp.application.dto.UserParamResponse;
import ru.lakeevda.camundaapp.domain.entity.user.User;
import ru.lakeevda.camundaapp.domain.entity.user.UserBirthday;
import ru.lakeevda.camundaapp.domain.entity.user.UserEmail;
import ru.lakeevda.camundaapp.domain.entity.user.UserFio;

public class UserMapper {

    public static User toDomain(UserParamRequest param) {
        return User.create(
                UserFio.of(param.fio()),
                UserBirthday.of(param.birthday()),
                UserEmail.of(param.email()));
    }

    public static UserParamResponse fromDomain(User domain) {
        return new UserParamResponse(
                domain.getId().getValue(),
                domain.getFio().getValue(),
                domain.getBirthday().getValue(),
                domain.getEmail().getValue());
    }
}
