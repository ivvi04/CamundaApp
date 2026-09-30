package ru.lakeevdan.camundaapp.domain.entity.user;

import lombok.Getter;

import java.time.LocalDate;

/**
 * Domain entity representing a user.
 */
@Getter
public class User {
    private Long id;
    private final UserFio name;
    private final LocalDate birthday;
    private final UserEmail email;

    private User(UserFio name, LocalDate birthday, UserEmail email) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
    }

    private User(Long id, UserFio name, LocalDate birthday, UserEmail email) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
    }

    public static User create(UserFio name, LocalDate birthday, UserEmail email) {
        return new User(name, birthday, email);
    }

    public static User restore(Long id, UserFio name, LocalDate birthday, UserEmail email) {
        return new User(id, name, birthday, email);
    }
}
