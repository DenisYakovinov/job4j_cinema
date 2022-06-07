package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.List;

public interface TicketService extends GenericService<Ticket> {
    List<Ticket> findAllBySessionId(int sessionId);

    List<Ticket> findAllByUserId(int userId);

    List<Ticket> createListToView(int sessionId);
}
