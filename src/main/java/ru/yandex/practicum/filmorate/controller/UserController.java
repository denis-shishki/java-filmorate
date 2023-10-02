package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> findAllUsers() {
        log.info("Получен запрос GET /users.");
        return userService.findAllUsers();
    }

    @GetMapping("{id}")
    public User findUserById(@PathVariable int id) throws NotFoundException {
        log.info("Получен запрос GET /users/{id}.");
        return userService.getUserById(id);
    }

    @PostMapping()
    public User createUser(@RequestBody User user) throws ValidationException {
        log.info("Получен запрос POST /users.");
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws Exception {
        log.info("Получен запрос PUT /users.");
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) throws NotFoundException {
        log.info("Получен запрос PUT /users{id}/friends/{friendId}.");
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) throws NotFoundException {
        log.info("Получен запрос DELETE /users{id}/friends/{friendId}.");
        userService.deleteFriend(id, friendId);
    }

    @RequestMapping("{id}/friends")
    @GetMapping
    public List<User> findFriends(@PathVariable int id) throws NotFoundException {
        log.info("Получен запрос GET /users{id}/friends.");
        return userService.getAllFriends(id);
    }

    @RequestMapping("{id}/friends/common/{otherId}")
    @GetMapping
    public List<User> findMutualFriends(@PathVariable int id, @PathVariable int otherId) throws NotFoundException {
        log.info("Получен запрос GET /users{id}/friends/common/{otherId}.");
        return userService.getMutualFriends(id, otherId);
    }
}
