package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final UserDbStorage userStorage;

    @Test
    public void testFindUserById() {

        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testFindAllUsers() {
        User user = User.builder()
                .name("Имя")
                .login("Логин")
                .email("почта@точка.ру")
                .birthday(LocalDate.ofEpochDay(2000 - 5 - 5))
                .build();
        User userResponse = userStorage.createUser(user);
        int lustId = userResponse.getId();

        List<User> users = userStorage.findAllUsers();
        assertThat(users).hasSize(lustId);
    }

    @Test
    public void testCreateUser() {
        User user = User.builder()
                .id(5)
                .name("Имя")
                .login("Логин")
                .email("почта@точка.ру")
                .birthday(LocalDate.ofEpochDay(2000 - 5 - 5))
                .build();
        User userResponse = userStorage.createUser(user);
        assertEquals(user, userResponse, "Пользователь создан некорректно");
    }

    @Test
    public void testUpdateUser() {
        User user = User.builder()
                .id(2)
                .name("Имя")
                .login("Логин")
                .email("почта@точка.ру")
                .birthday(LocalDate.ofEpochDay(2000 - 5 - 5))
                .build();
        User userResponse = userStorage.updateUser(user);
        assertEquals(user, userResponse, "Пользователь обновлен некорректно");
    }

    @Test
    public void testAddFriend() {
        userStorage.addFriend(4, 1);
        List<User> friends = userStorage.findFriendsByUserId(4);
        assertThat(friends).hasSize(1);
    }

    @Test
    public void testDeleteFriend() {
        userStorage.deleteFriend(2, 1);
        List<User> friends = userStorage.findFriendsByUserId(2);
        assertThat(friends).hasSize(1);
    }

    @Test
    public void testExistsUserById() {
        assertTrue(userStorage.existsUserById(1), "Метод не находит существующий id");
        assertFalse(userStorage.existsUserById(9999), "Метод находит несуществующий id");
    }
}
