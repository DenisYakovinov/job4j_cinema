package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface UserService extends GenericService<User> {

    Optional<User> findUserByEmailAndPwd(String email, String pass);
}
