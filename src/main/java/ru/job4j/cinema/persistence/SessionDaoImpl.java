package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.exception.DaoException;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.persistence.mapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SessionDaoImpl implements SessionDao {

    private final BasicDataSource pool;
    private final RowMapper<Session> sessionMapper;

    public SessionDaoImpl(BasicDataSource pool, RowMapper<Session> sessionMapper) {
        this.pool = pool;
        this.sessionMapper = sessionMapper;
    }

    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM sessions ORDER BY id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(sessionMapper.mapRow(it));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't get sessions (%s)", e.getMessage()), e);
        }
        return sessions;
    }

    @Override
    public Optional<Session> add(Session session) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO sessions (name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, session.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    session.setId(id.getInt(1));
                }
            }
            return Optional.of(session);
        } catch (SQLException e) {
            throw new DaoException(String.format("Session %s can't be added (%s)", session, e.getMessage()), e);
        }
    }

    @Override
    public void update(Session session) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE sessions SET (name) = (?) WHERE id = ?")
        ) {
            ps.setString(1, session.getName());
            ps.setInt(2, session.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't update session {%s} (%s)", session, e.getMessage()), e);
        }
    }

    @Override
    public Optional<Session> findById(int id) throws DaoException {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM sessions WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(sessionMapper.mapRow(it));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't find session by id {%s} (%s)", id, e.getMessage()), e);
        }
        return Optional.empty();
    }
}
