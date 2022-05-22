package ru.job4j.cinema.exception;

public class TicketBookedException extends ServiceException {

    public TicketBookedException(String message) {
        super(message);
    }

    public TicketBookedException(String message, Throwable cause) {
        super(message, cause);
    }
}
