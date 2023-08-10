package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidator {
    public static void validate(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getEmail() == null ||user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Некорректный ввод данных");
            throw new ValidationException("Введен некорректный email.");
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.warn("Некорректный ввод данных");
            throw new ValidationException("Логин не может быть пустым и содержать пробелов.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Некорректный ввод данных");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }
}

