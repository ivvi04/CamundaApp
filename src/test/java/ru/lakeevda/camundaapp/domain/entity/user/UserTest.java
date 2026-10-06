package ru.lakeevda.camundaapp.domain.entity.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserTest {

    // ---- create() — успешные сценарии ----

    @Test
    void create_success() {
        User user = User.create(UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com"));

        assertNotNull(user);
        assertNull(user.getId()); // без ID в create()
        assertEquals("Ivan Ivanov", user.getFio().getValue());
    }

    @Test
    void create_fioIsNotNull() {
        User user = User.create(UserFio.of("Petrov P.P."), UserBirthday.of(java.time.LocalDate.of(1985, 6, 15)), UserEmail.of("petrov@test.com"));
        assertEquals("Petrov P.P.", user.getFio().getValue());
    }

    @Test
    void create_birthdayIsNotNull() {
        User user = User.create(UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com"));
        assertEquals(java.time.LocalDate.of(1990, 1, 1), user.getBirthday().getValue());
    }

    @Test
    void create_emailIsNotNull() {
        User user = User.create(UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com"));
        assertEquals("ivan@example.com", user.getEmail().getValue());
    }

    // ---- create() — отрицательные сценарии (null-проверки) ----

    @Test
    void create_throwsWhenFioIsNull() {
        assertThrows(IllegalArgumentException.class, () -> User.create(null, UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com")));
    }

    @Test
    void create_throwsWhenBirthdayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> User.create(UserFio.of("Ivan Ivanov"), null, UserEmail.of("ivan@example.com")));
    }

    @Test
    void create_throwsWhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class, () -> User.create(UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), null));
    }

    // ---- restore() — успешные сценарии ----

    @Test
    void restore_success() {
        UserId id = UserId.of(42L);
        User user = User.restore(id, UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com"));

        assertNotNull(user);
        assertEquals(42L, user.getId().getValue());
    }

    @Test
    void restore_idIsNotNull() {
        UserId id = UserId.of(7L);
        User user = User.restore(id, UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com"));
        assertEquals(7L, user.getId().getValue());
    }

    @Test
    void restore_fioIsNotNull() {
        UserId id = UserId.of(1L);
        User user = User.restore(id, UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com"));
        assertEquals("Ivan Ivanov", user.getFio().getValue());
    }

    @Test
    void restore_birthdayIsNotNull() {
        UserId id = UserId.of(1L);
        User user = User.restore(id, UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com"));
        assertEquals(java.time.LocalDate.of(1990, 1, 1), user.getBirthday().getValue());
    }

    @Test
    void restore_emailIsNotNull() {
        UserId id = UserId.of(1L);
        User user = User.restore(id, UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com"));
        assertEquals("ivan@example.com", user.getEmail().getValue());
    }

    // ---- restore() — отрицательные сценарии (null-проверки) ----

    @Test
    void restore_throwsWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> User.restore(null, UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com")));
    }

    @Test
    void restore_throwsWhenFioIsNull() {
        assertThrows(IllegalArgumentException.class, () -> User.restore(UserId.of(2L), null, UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), UserEmail.of("ivan@example.com")));
    }

    @Test
    void restore_throwsWhenBirthdayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> User.restore(UserId.of(3L), UserFio.of("Ivan Ivanov"), null, UserEmail.of("ivan@example.com")));
    }

    @Test
    void restore_throwsWhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class, () -> User.restore(UserId.of(4L), UserFio.of("Ivan Ivanov"), UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)), null));
    }
}
