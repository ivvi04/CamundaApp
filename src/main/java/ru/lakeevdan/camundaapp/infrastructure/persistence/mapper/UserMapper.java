package ru.lakeevdan.camundaapp.infrastructure.persistence.mapper;

import ru.lakeevdan.camundaapp.domain.entity.user.User;
import ru.lakeevdan.camundaapp.domain.entity.user.UserEmail;
import ru.lakeevdan.camundaapp.domain.entity.user.UserFio;
import ru.lakeevdan.camundaapp.infrastructure.persistence.entity.UserEntity;

public class UserMapper {

    public static User fromEntity(UserEntity entity) {
        return User.restore(entity.getId(),
                UserFio.of(entity.getFio()),
                entity.getBirthday(),
                UserEmail.of(entity.getEmail()));
    }
}
