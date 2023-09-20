package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    protected Set<Integer> idsUsersLike;
    protected final int id;
    protected final String name;
    protected final String description;
    protected final LocalDate releaseDate;
    protected final int duration;

    public int getNumberOfLikes() {
        return idsUsersLike.size();
    }

    public void addLike(int idUser) {
        idsUsersLike.add(idUser);
    }

    public void deleteLike(int idUser) {
        idsUsersLike.remove(idUser);
    }
}
