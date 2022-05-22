package ru.job4j.cinema.persistence.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.cinema.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"),
                resultSet.getString("phone"), resultSet.getString("password"));
    }
}
