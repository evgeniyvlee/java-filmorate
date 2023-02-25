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
     * @throws DataAlreadyExistException occurs if data is already exist ind storage
     */
    T create(T data) throws DataAlreadyExistException;

    /**
     * Update data instance in storage
     * @param data data instance
     * @return updated data
     * @throws DataNotFoundException occurs if data not found in storage
     */
    T update(T data) throws DataNotFoundException;

    /**
     * Get data instance from storage by ID
     * @param id data ID
     * @return found data
     * @throws DataNotFoundException occurs if data not found in storage
     */
    T get(long id) throws DataNotFoundException;

    /**
     * Delete data instance from storage by id
     * @param id data ID
     * @throws DataNotFoundException occurs if data not found in storage
     */
    void delete(long id) throws DataNotFoundException;
}
