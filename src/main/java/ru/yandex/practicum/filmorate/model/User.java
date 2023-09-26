package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    protected int id;
    protected final String email;
    protected final String login;
    protected String name;
    protected final LocalDate birthday;

}
