package ru.job4j.cinema.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    List<T> findAll();

    Optional<T> add(T entity);

    void update(T entity);

    Optional<T> findById(int id);
}
