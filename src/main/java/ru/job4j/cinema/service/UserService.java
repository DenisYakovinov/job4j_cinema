package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.UserDaoImpl;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements GenericService<User> {

    private final UserDaoImpl userDao;

    public UserService(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public Optional<User> add(User user) {
        return userDao.add(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public Optional<User> findById(int id) {
        return userDao.findById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public Optional<User> findUserByEmailAndPwd(String email, String pass) {
        return userDao.findUserByEmailAndPwd(email, pass);
    }
}
