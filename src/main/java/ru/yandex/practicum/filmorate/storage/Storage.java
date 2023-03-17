package ru.yandex.practicum.filmorate.storage;

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
     */
    void create(T data);

    /**
     * Get data by ID from storage
     * @param id data ID
     * @return found data
     */
    T get(long id);

    /**
     * Update data in storage
     * @param data data
     */
    void update(T data);

    /**
     * Delete data in storage by ID
     * @param id data ID
     */
    void delete(long id);

    /**
     * Get all data from storage
     * @return list of all data
     */
    List<T> getAll();
}
