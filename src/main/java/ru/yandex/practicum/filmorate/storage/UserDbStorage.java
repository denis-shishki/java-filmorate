package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class UserDbStorage implements UserDao {
    protected final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAllUsers() {
        String sqlQuery = "select * from users";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into users (name, login, email, birthday)" +
                "values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);

        int userId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        user.setId(userId);
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "update users set name = ?, login = ?, email = ?, birthday = ?" +
                " where user_id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    @Override
    public User getUserById(int id) {
        String sqlQuery = "select * from users where user_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
    }

    @Override
    public List<User> findFriendsByUserId(int id) {
        String sqlQuery = "select * from users where user_id in " +
                "(select friend_id from friends_list where user_id = ?)";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id);
    }

    @Override
    public void addFriend(int userId, int friendId) {
        String sqlQuery = "insert into friends_list (user_id, friend_id, confirmation)" +
                "values (?, ?, 'f')";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        String sqlQuery = "DELETE FROM friends_list WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    protected User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("user_id"))
                .name(rs.getString("name"))
                .login(rs.getString("login"))
                .email(rs.getString("email"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }

    @Override
    public boolean existsUserById(int userId) {
        String sql = "select count(*) from users where user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        if (count == null) {
            return false;
        }
        return count > 0;
    }
}
