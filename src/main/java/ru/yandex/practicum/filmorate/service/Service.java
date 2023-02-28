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
     */
    T create(T data);

    /**
     * Update data in storage
     * @param data data
     * @return updated data
     */
    T update(T data);

    /**
     * Delete data in storage by id
     * @param id data ID
     */
    void delete(long id);

    /**
     * Get data from storage y id
     * @param id data ID
     * @return found data
     */
    T get(long id);
}
