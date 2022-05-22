package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.persistence.SessionDaoImpl;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService implements GenericService<Session> {

    private final SessionDaoImpl sessionDao;

    public SessionService(SessionDaoImpl sessionDao) {
        this.sessionDao = sessionDao;
    }

    public Optional<Session> add(Session session) {
        return sessionDao.add(session);
    }

    public void update(Session session) {
        sessionDao.update(session);
    }

    public Optional<Session> findById(int id) {
        return sessionDao.findById(id);
    }

    public List<Session> findAll() {
        return sessionDao.findAll();
    }
}
