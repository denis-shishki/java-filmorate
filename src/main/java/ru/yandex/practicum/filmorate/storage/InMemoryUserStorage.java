package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) throws ValidationException {
        UserValidator.validate(user);
        User newUser = User.builder()
                .id(nextId++)
                .email(user.getEmail())
                .login(user.getLogin())
                .name(user.getName())
                .birthday(user.getBirthday())
                .friendsId(user.getFriendsId())
                .build();
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public User updateUser(User user) throws ValidationException, UserNotFoundException {
        if (users.containsKey(user.getId())) {
            UserValidator.validate(user);
            users.put(user.getId(), user);
            return user;
        } else {
            throw new UserNotFoundException("Пользователь с таким идентификатором не найден.");
        }
    }

    public User getUserById(int id) {
        return users.get(id);
    }
}
