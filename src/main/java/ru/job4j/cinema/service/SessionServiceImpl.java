package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.persistence.SessionDao;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionDao sessionDao;

    public SessionServiceImpl(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Override
    public Optional<Session> add(Session session) {
        return sessionDao.add(session);
    }

    @Override
    public void update(Session session) {
        sessionDao.update(session);
    }

    @Override
    public Optional<Session> findById(int id) {
        return sessionDao.findById(id);
    }

    @Override
    public List<Session> findAll() {
        return sessionDao.findAll();
    }
}
