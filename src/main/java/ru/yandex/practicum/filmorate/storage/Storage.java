package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.DataAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Data;
import java.util.List;

/**
 * Storage interface provides methods for CRUD operations
 * @param <T> data type
 * @author Evgeniy Lee
 */
public interface Storage<T extends Data> {
    /**
     * Put data in storage
     * @param data data
     * @throws DataAlreadyExistException occurs if data is already exists in storage
     */
    void create(T data) throws DataAlreadyExistException;

    /**
     * Get data by ID from storage
     * @param id data ID
     * @return found data
     * @throws DataNotFoundException occurs if data not found in storage
     */
    T get(long id) throws DataNotFoundException;

    /**
     * Update data in storage
     * @param data data
     * @throws DataNotFoundException occurs if data not found in storage
     */
    void update(T data) throws DataNotFoundException;

    /**
     * Delete data in storage by ID
     * @param id data ID
     * @throws DataNotFoundException occurs if data not found in storage
     */
    void delete(long id) throws DataNotFoundException;

    /**
     * Get all data from storage
     * @return list of all data
     */
    List<T> getAll();
}
