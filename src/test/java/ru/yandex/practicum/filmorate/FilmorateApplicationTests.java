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

    static User user = User.builder()
            .name("Имя")
            .login("Логин")
            .email("почта@точка.ру")
            .birthday(LocalDate.ofEpochDay(2000 - 5 - 5))
            .build();


    @Test
    public void testFindUserById() {
        User newUser = userStorage.createUser(user);
        int userId = newUser.getId();
        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(userId));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", userId)
                );
    }

    @Test
    public void testFindAllUsers() {
        User userResponse = userStorage.createUser(user);
        int lustId = userResponse.getId();

        List<User> users = userStorage.findAllUsers();
        assertThat(users).hasSize(lustId);
    }

    @Test
    public void testCreateUser() {
        User newUser = User.builder()
                .name("newName")
                .login("newЛогин")
                .email("newпочта@точка.ру")
                .birthday(LocalDate.ofEpochDay(1999 - 5 - 5))
                .build();
        User userResponse = userStorage.createUser(newUser);
        newUser.setId(userResponse.getId());
        assertEquals(newUser, userResponse, "Пользователь создан некорректно");
    }

    @Test
    public void testUpdateUser() {
        User newUser = userStorage.createUser(user);
        int userId = newUser.getId();

        User updateUser = User.builder()
                .id(userId)
                .name("НовоеИмя")
                .login("НовыйЛогин")
                .email("новаяПочта@точка.ру")
                .birthday(LocalDate.ofEpochDay(1900 - 5 - 5))
                .build();
        User userResponse = userStorage.updateUser(updateUser);
        assertEquals(updateUser, userResponse, "Пользователь обновлен некорректно");
    }

    @Test
    public void testAddFriend() {
        User firstUser = userStorage.createUser(user);
        User secondUser = userStorage.createUser(user);

        int firstId = firstUser.getId();
        int secondId = secondUser.getId();
        userStorage.addFriend(firstId, secondId);
        List<User> friends = userStorage.findFriendsByUserId(firstId);
        assertThat(friends).hasSize(1);
    }

    @Test
    public void testDeleteFriend() {
        User firstUser = userStorage.createUser(user);
        User secondUser = userStorage.createUser(user);

        int firstId = firstUser.getId();
        int secondId = secondUser.getId();
        userStorage.addFriend(firstId, secondId);
        List<User> friends = userStorage.findFriendsByUserId(firstId);
        assertThat(friends).hasSize(1);

        userStorage.deleteFriend(firstId, secondId);
        List<User> updateFriends = userStorage.findFriendsByUserId(firstId);
        assertThat(updateFriends).hasSize(0);
    }

    @Test
    public void testExistsUserById() {
        userStorage.createUser(user);
        assertTrue(userStorage.existsUserById(1), "Метод не находит существующий id");
        assertFalse(userStorage.existsUserById(9999), "Метод находит несуществующий id");
    }
}
