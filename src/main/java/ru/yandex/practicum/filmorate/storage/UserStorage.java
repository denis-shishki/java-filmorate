package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    public List<User> findAllUsers();

    public User createUser(User user) throws ValidationException;

    public User updateUser(User user) throws ValidationException, NotFoundException;

    public User getUserById(int id);
}
