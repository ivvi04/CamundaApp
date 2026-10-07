package ru.lakeevda.camundaapp.infrastructure.persistence.mapper;

import ru.lakeevda.camundaapp.domain.model.user.*;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        if (user.getId() != null) entity.setId(user.getId().getValue());
        entity.setFio(user.getFio().getValue());
        entity.setBirthday(user.getBirthday().getValue());
        entity.setEmail(user.getEmail().getValue());
        return entity;
    }

    public static User toDomain(UserEntity entity) {
        return User.restore(UserId.of(entity.getId()),
                UserFio.of(entity.getFio()),
                UserBirthday.of(entity.getBirthday()),
                UserEmail.of(entity.getEmail()));
    }
}
