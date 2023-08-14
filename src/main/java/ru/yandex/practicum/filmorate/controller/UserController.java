package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/users")
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @GetMapping()
    public List<User> findAllUsers() {
        log.info("Получен запрос GET /users.");
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User createUser(@RequestBody User user) throws ValidationException /*throws UserAlreadyExistException, InvalidEmailException*/ {
        log.info("Получен запрос POST /users.");
        UserValidator.validate(user);
        User newUser = User.builder()
                .id(nextId++)
                .email(user.getEmail())
                .login(user.getLogin())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build();
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws Exception {
        log.info("Получен запрос PUT /users.");
        if (users.containsKey(user.getId())) {
            UserValidator.validate(user);
            users.put(user.getId(), user);
            return user;
        } else {
            throw new UserNotFoundException("Пользователь с таким идентификатором не найден.");
        }
    }
}
