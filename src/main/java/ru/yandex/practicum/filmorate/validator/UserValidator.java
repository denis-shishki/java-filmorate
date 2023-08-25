package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectCountException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.HashSet;

@Slf4j
@Component
public class UserValidator {
    public static void validate(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Некорректный ввод данных: Введен некорректный email.");
            throw new ValidationException("Введен некорректный email.");
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.warn("Некорректный ввод данных: Логин не может быть пустым и содержать пробелов.");
            throw new ValidationException("Логин не может быть пустым и содержать пробелов.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Некорректный ввод данных: Дата рождения не может быть в будущем.");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        } else if (user.getFriendsId() == null) {
            user.setFriendsId(new HashSet<>());
        }
    }

    public static void checkCorrectVariableIdUser(UserStorage userStorage, int userId) throws UserNotFoundException {
        if (userId <= 0) {
            throw new IncorrectCountException("Id пользователя не может быть 0 или меньше");
        } else if (userStorage.getUserById(userId) == null) {
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
    }
}

