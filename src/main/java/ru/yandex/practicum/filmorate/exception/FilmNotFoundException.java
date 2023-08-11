package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends Exception {
    public FilmNotFoundException(String massage) {
        super(massage);
    }
}
