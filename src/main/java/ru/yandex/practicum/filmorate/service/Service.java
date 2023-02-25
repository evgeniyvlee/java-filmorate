package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.DataAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Data;
import java.util.List;

/**
 * Service interface provides methods for CRUD operations
 * @param <T> data type
 * @author Evgeniy Lee
 */
public interface Service<T extends Data> {
    /**
     * Get all data
     * @return list of data
     */
    List<T> getAll();

    /**
     * Create data and save in storage
     * @param data data
     * @return created data
     * @throws DataAlreadyExistException occurs if data is already exists in storage
     */
    T create(T data) throws DataAlreadyExistException;

    /**
     * Update data in storage
     * @param data data
     * @return updated data
     * @throws DataNotFoundException occurs if data not found in starage
     */
    T update(T data) throws DataNotFoundException;

    /**
     * Delete data in storage by id
     * @param id data ID
     * @throws DataNotFoundException occurs if data not found in starage
     */
    void delete(long id) throws DataNotFoundException;

    /**
     * Get data from storage y id
     * @param id data ID
     * @return found data
     * @throws DataNotFoundException occurs if data not found in starage
     */
    T get(long id) throws DataNotFoundException;
}
