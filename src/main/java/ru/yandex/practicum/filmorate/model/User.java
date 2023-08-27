package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    protected Set<Integer> friendsId;
    protected final int id;
    protected final String email;
    protected final String login;
    protected String name;
    protected final LocalDate birthday;

    public void addIdFriend(int id) {
        friendsId.add(id);
    }

    public void deleteFriend(int id) {
        friendsId.remove(id);
    }
}
