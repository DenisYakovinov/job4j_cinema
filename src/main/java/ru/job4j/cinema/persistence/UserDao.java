package ru.job4j.cinema.persistence;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    Optional<User> findUserByEmailAndPwd(String email, String pass);
}
