package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.SessionDao;
import ru.job4j.cinema.persistence.TicketDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private static final int MAX_ROWS = 4;
    private static final int MAX_SETS = 4;

    private final TicketDao ticketDao;
    private final SessionDao sessionDao;

    public TicketServiceImpl(TicketDao ticketDao, SessionDao sessionDao) {
        this.ticketDao = ticketDao;
        this.sessionDao = sessionDao;
    }

    @Override
    public Optional<Ticket> add(Ticket ticket) {
        return ticketDao.add(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        ticketDao.update(ticket);
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return ticketDao.findById(id);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }

    @Override
    public List<Ticket> findAllBySessionId(int sessionId) {
        return ticketDao.findAllBySessionId(sessionId);
    }

    @Override
    public List<Ticket> findAllByUserId(int userId) {
        return ticketDao.findAllByUserId(userId);
    }

    @Override
    public List<Ticket> createListToView(int sessionId) {
        List<Ticket> tickets = findAllBySessionId(sessionId);
        List<Ticket> result = new ArrayList<>();
        int counter = 0;
        for (int i = 1; i <= MAX_ROWS; i++) {
            for (int j = 1; j <= MAX_SETS; j++) {
                Ticket ticket = null;
                if (tickets.size() > counter) {
                    ticket = tickets.get(counter);
                }
                if (ticket != null && ticket.getRow() == i && ticket.getCell() == j) {
                    result.add(ticket);
                    counter++;
                } else {
                    Session session = sessionDao.findById(sessionId).orElse(new Session(""));
                    result.add(new Ticket(session, i, j, 0));
                }
            }
        }
        return result;
    }
}
