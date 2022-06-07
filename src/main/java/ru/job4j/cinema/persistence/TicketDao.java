package ru.job4j.cinema.persistence;

import ru.job4j.cinema.model.Ticket;

import java.util.List;

public interface TicketDao extends GenericDao<Ticket> {

    List<Ticket> findAllBySessionId(int sessionId);

    public List<Ticket> findAllByUserId(int userId);
}
