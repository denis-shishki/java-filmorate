package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    private final UserDao userStorage;

    @Autowired
    public UserService(UserDao userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int userId, int friendId) throws NotFoundException {
        UserValidator.checkCorrectVariableIdUser(userStorage, userId);
        UserValidator.checkCorrectVariableIdUser(userStorage, friendId);
        
        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) throws NotFoundException { //а как понять, где чья дружба
        UserValidator.checkCorrectVariableIdUser(userStorage, userId);
        UserValidator.checkCorrectVariableIdUser(userStorage, friendId);

        userStorage.deleteFriend(userId, friendId);
    }

    public List<User> getAllFriends(int userId) throws NotFoundException { //может вернуть оптионал и проверить?
        UserValidator.checkCorrectVariableIdUser(userStorage, userId);
        return userStorage.findFriendsByUserId(userId);
    }

    public List<User> getMutualFriends(int userId, int otherId) throws NotFoundException { //общие друзья
        UserValidator.checkCorrectVariableIdUser(userStorage, userId);
        UserValidator.checkCorrectVariableIdUser(userStorage, otherId);

        List<User> friendsIdsByUser = userStorage.findFriendsByUserId(userId);
        List<User> friendsIdsByOther = userStorage.findFriendsByUserId(otherId);
        List<User> mutualFriends = new ArrayList<>();

        for (User user : friendsIdsByUser) {
            if (friendsIdsByOther.contains(user)) {
                mutualFriends.add(user);
            }
        }
        return mutualFriends;
    }

    public User getUserById(int id) throws NotFoundException {
        UserValidator.checkCorrectVariableIdUser(userStorage, id);
        return userStorage.getUserById(id);
    }

    public List<User> findAllUsers() throws NotFoundException { //может вернуть оптионал и проверить?
        return userStorage.findAllUsers();
    }

    public User createUser(User user) throws ValidationException {
        UserValidator.validate(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) throws ValidationException, NotFoundException {
        UserValidator.validate(user);
        UserValidator.checkCorrectVariableIdUser(userStorage, user.getId());
        return userStorage.updateUser(user);
    }
}
