package ru.job4j.cinema.persistence.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.cinema.model.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SessionMapper implements RowMapper<Session> {

    @Override
    public Session mapRow(ResultSet resultSet) throws SQLException {
        return new Session(resultSet.getInt("id"), resultSet.getString("name"));
    }

    public Session mapRow(ResultSet resultSet, int resultSetIdNumber) throws SQLException {
        return new Session(resultSet.getInt(resultSetIdNumber), resultSet.getString("name"));
    }
}
