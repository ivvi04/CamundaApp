package ru.lakeevda.camundaapp.domain.entity.user;

import lombok.Getter;

/**
 * Domain entity representing a user.
 */
@Getter
public class User {
    private UserId id;
    private final UserFio fio;
    private final UserBirthday birthday;
    private final UserEmail email;

    private User(UserFio fio, UserBirthday birthday, UserEmail email) {
        this.fio = fio;
        this.birthday = birthday;
        this.email = email;
    }

    private User(UserId id, UserFio fio, UserBirthday birthday, UserEmail email) {
        this.id = id;
        this.fio = fio;
        this.birthday = birthday;
        this.email = email;
    }

    public static User create(UserFio fio, UserBirthday birthday, UserEmail email) {
        if (fio == null || birthday == null || email == null) {
            throw new IllegalArgumentException("User fio or birthday or email are null");
        }

        return new User(fio, birthday, email);
    }

    public static User restore(UserId id, UserFio fio, UserBirthday birthday, UserEmail email) {
        if (id == null || fio == null || birthday == null || email == null) {
            throw new IllegalArgumentException("User id or fio or birthday or email are null");
        }
        return new User(id, fio, birthday, email);
    }
}
