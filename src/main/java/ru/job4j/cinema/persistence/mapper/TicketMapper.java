package ru.job4j.cinema.persistence.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.cinema.model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TicketMapper implements RowMapper<Ticket> {

    private static final int SESSION_ID_IN_RESULT_SET = 6;
    private final SessionMapper sessionMapper;

    public TicketMapper(SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
    }

    @Override
    public Ticket mapRow(ResultSet resultSet) throws SQLException {
        return new Ticket(resultSet.getInt("id"), sessionMapper.mapRow(resultSet, SESSION_ID_IN_RESULT_SET), resultSet.getInt("row"),
                resultSet.getInt("cell"), resultSet.getInt("user_id"));
    }
}
