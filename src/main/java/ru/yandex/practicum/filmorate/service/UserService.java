package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.userStorage = inMemoryUserStorage;
    }

    public User addFriend(int idUser, int idFriend) throws NotFoundException {
        UserValidator.checkCorrectVariableIdUser(userStorage, idUser);
        UserValidator.checkCorrectVariableIdUser(userStorage, idFriend);

        User user = userStorage.getUserById(idUser);
        User friend = userStorage.getUserById(idFriend);

        user.addIdFriend(idFriend);
        friend.addIdFriend(idUser);
        return user;
    }

    public User deleteFriend(int idUser, int idFriend) throws NotFoundException {
        UserValidator.checkCorrectVariableIdUser(userStorage, idUser);
        UserValidator.checkCorrectVariableIdUser(userStorage, idFriend);

        User user = userStorage.getUserById(idUser);
        User friend = userStorage.getUserById(idFriend);

        user.deleteFriend(idFriend);
        friend.deleteFriend(idUser);
        return user;
    }

    public List<User> getAllFriends(int idUser) throws NotFoundException {
        UserValidator.checkCorrectVariableIdUser(userStorage, idUser);
        User user = userStorage.getUserById(idUser);
        HashSet<Integer> friendsId = (HashSet<Integer>) user.getFriendsId();
        List<User> friends = new ArrayList<>();
        for (Integer id : friendsId) {
            friends.add(userStorage.getUserById(id));
        }
        return friends;
    }

    public List<User> getMutualFriends(int idUser, int idFriend) throws NotFoundException {
        UserValidator.checkCorrectVariableIdUser(userStorage, idUser);
        UserValidator.checkCorrectVariableIdUser(userStorage, idFriend);

        User user = userStorage.getUserById(idUser);
        User other = userStorage.getUserById(idFriend);

        Set<Integer> friendsIdsByUser = user.getFriendsId();
        Set<Integer> friendsIdsByOther = other.getFriendsId();
        List<User> mutualFriends = new ArrayList<>();

        for (Integer id : friendsIdsByUser) {
            if (friendsIdsByOther.contains(id)) {
                mutualFriends.add(userStorage.getUserById(id));
            }
        }
        return mutualFriends;
    }

    public User getUserById(int id) throws NotFoundException {
        UserValidator.checkCorrectVariableIdUser(userStorage, id);
        return userStorage.getUserById(id);
    }
}
