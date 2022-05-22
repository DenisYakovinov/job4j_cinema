package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.exception.DaoException;
import ru.job4j.cinema.exception.UniqueViolationException;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.mapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements GenericDao<User> {
    private final BasicDataSource pool;
    private final RowMapper<User> userMapper;

    public UserDaoImpl(BasicDataSource pool, RowMapper<User> userMapper) {
        this.pool = pool;
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users ORDER BY id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(userMapper.mapRow(it));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't get users (%s)", e.getMessage()), e);
        }
        return users;
    }

    public Optional<User> add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO users (name, email, phone, password) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            return Optional.of(user);
        } catch (SQLException e) {
            if (e instanceof PSQLException && e.getSQLState().equals("23505")) {
                throw new UniqueViolationException(e.getMessage(), e);
            }
            throw new DaoException(String.format("User %s can't be added (%s)", user, e.getMessage()), e);
        }
    }

    public void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE users SET (name, email, phone, password) = (?, ?, ?, ?) WHERE id = ?")
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't update user {%s} (%s)", user, e.getMessage()), e);
        }
    }

    public Optional<User> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(userMapper.mapRow(it));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Can't find user by id {%s} (%s)", id, e.getMessage()), e);
        }
        return Optional.empty();
    }

    public Optional<User> findUserByEmailAndPwd(String email, String pass) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, pass);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(userMapper.mapRow(it));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("can't get user by email and pass {%s} (%s)", email, e.getMessage()), e);
        }
        return Optional.empty();
    }
}
