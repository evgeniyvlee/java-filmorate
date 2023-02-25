package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.DataAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Data;
import java.util.List;

/**
 * Abstract controller provides methods for creating, updating and getting data from storage.
 * @param <T> data type
 * @author Evgeniy Lee
 */
public interface Controller<T extends Data> {
    /**
     * Get all data from storage
     * @return list of data
     */
    List<T> getAll();

    /**
     * Create new data instance in storage
     * @param data data instance
     * @return created data
     */
    T create(T data) throws DataAlreadyExistException;

    /**
     * Update data instance in storage
     * @param data data instance
     * @return updated data
     */
    T update(T data) throws DataNotFoundException;

    T get(long id) throws DataNotFoundException;

    void delete(long id) throws DataNotFoundException;
}
