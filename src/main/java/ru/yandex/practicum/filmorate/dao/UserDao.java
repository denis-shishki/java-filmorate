package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAllUsers();

    User createUser(User user) throws ValidationException;

    User updateUser(User user) throws ValidationException, NotFoundException;

    User getUserById(int id);

    List<User> findFriendsByUserId(int id);

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    boolean existsUserById(int userId);
}
