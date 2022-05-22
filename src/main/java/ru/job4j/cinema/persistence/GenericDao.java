package ru.job4j.cinema.persistence;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {

    List<T> findAll();

    Optional<T> add(T entity);

    void update(T entity);

    Optional<T> findById(int id);
}
