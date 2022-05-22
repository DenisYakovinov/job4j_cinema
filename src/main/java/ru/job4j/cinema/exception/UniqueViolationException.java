package ru.job4j.cinema.exception;

public class UniqueViolationException extends DaoException {

    public UniqueViolationException(String message) {
        super(message);
    }

    public UniqueViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
