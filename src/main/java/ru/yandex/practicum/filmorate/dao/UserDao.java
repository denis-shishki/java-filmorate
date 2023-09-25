package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao {
    public List<User> findAllUsers();

    public User createUser(User user) throws ValidationException;

    public User updateUser(User user) throws ValidationException, NotFoundException;

    public User getUserById(int id);

    List<User> findFriendsByUserId(int id);

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    boolean existsUserById(int userId);
}
