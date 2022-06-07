package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.exception.DaoException;
import ru.job4j.cinema.exception.UniqueViolationException;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.mapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDaoImpl implements TicketDao {

    private static final String BASE_SELECT_QUERY_PART = "SELECT * FROM ticket t JOIN sessions s on t.session_id = s.id";

    private final BasicDataSource pool;
    private final RowMapper<Ticket> ticketMapper;

    public TicketDaoImpl(BasicDataSource pool, RowMapper<Ticket> ticketMapper) {
        this.pool = pool;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public Optional<Ticket> add(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO ticket (session_id, row, cell, user_id)"
                     + " VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getSession().getId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
            return Optional.of(ticket);
        } catch (SQLException e) {
            if (e instanceof PSQLException && e.getSQLState().equals("23505")) {
                throw new UniqueViolationException(e.getMessage(), e);
            }
            throw new DaoException(String.format("Ticket %s can't be added (%s)", ticket, e.getMessage()), e);
        }
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(String.format("%s ORDER BY row, cell", BASE_SELECT_QUERY_PART));
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(ticketMapper.mapRow(it));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't get tickets (%s)", e.getMessage()), e);
        }
        return tickets;
    }

    @Override
    public List<Ticket> findAllBySessionId(int sessionId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(String.format("%s WHERE session_id = ? ORDER BY row, cell",
                     BASE_SELECT_QUERY_PART));
        ) {
            ps.setInt(1, sessionId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(ticketMapper.mapRow(it));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't get tickets by session id %s (%s)",
                    sessionId, e.getMessage()), e);
        }
        return tickets;
    }

    @Override
    public List<Ticket> findAllByUserId(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket t JOIN sessions"
                     + " s on t.session_id = s.id WHERE user_id = ? ORDER BY row, cell")
        ) {
            ps.setInt(1, userId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(ticketMapper.mapRow(it));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't get tickets by user id %s (%s)",
                    userId, e.getMessage()), e);
        }
        return tickets;
    }

    @Override
    public void update(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE ticket SET (session_id, row, cell, user_id)"
                     + " = (?, ?, ?, ?) WHERE id = ?")
        ) {
            ps.setInt(1, ticket.getSession().getId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.setInt(5, ticket.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't update ticket {%s} (%s)", ticket, e.getMessage()), e);
        }
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(String.format("%s WHERE id = ?", BASE_SELECT_QUERY_PART));
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(ticketMapper.mapRow(it));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't find ticket by id {%s} (%s)", id, e.getMessage()), e);
        }
        return Optional.empty();
    }
}
