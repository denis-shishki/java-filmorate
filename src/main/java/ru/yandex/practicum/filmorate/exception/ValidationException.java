package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends Exception{ // возможно ошибку валидации надо отдельно для каждого нюанса писать
    public ValidationException(String massage) {
        super(massage);
    }
}
