package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    @Test
    void userTry() {
        User user = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        assertDoesNotThrow(() -> UserValidator.validate(user));
    }

    @Test
    void userFailLogin() {
        User user = User.builder()
                .login("dol ore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () -> UserValidator.validate(user));
        assertEquals("Логин не может быть пустым и содержать пробелов.", exception.getMessage());
    }

    @Test
    void userFailEmail() {
        User user = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("maiil.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () -> UserValidator.validate(user));
        assertEquals("Введен некорректный email.", exception.getMessage());
    }

    @Test
    void userFailBirthday() {
        User user = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(3946, 8, 20))
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () -> UserValidator.validate(user));
        assertEquals("Дата рождения не может быть в будущем.", exception.getMessage());
    }

    @Test
    void userWithEmptyName() {
        User user = User.builder()
                .login("dolore")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        assertDoesNotThrow(() -> UserValidator.validate(user));
        assertEquals("dolore", user.getName());
    }
}