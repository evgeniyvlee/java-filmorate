package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for creating, updating and getting data.
 * @param <T> data type
 * @author Evgeniy Lee
 */
public abstract class ModelService<T extends Data> {
    // Map holding ids and data
    private final Map<Long, T> storage = new HashMap<>();
    // IDs increment every time when creating data
    private Long ids = 0L;

    /**
     * Get all data
     * @return list of data
     */
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Create data and save in storage
     * @param data data
     * @return created data
     */
    public T create(T data) {
        data.setId(++ids);
        storage.put(data.getId(), data);
        return data;
    }

    /**
     * Update data in storage
     * @param data data
     * @return updated data
     */
    public T update(T data) {
        if (storage.get(data.getId()) == null) {
            return null;
        }
        storage.put(data.getId(), data);
        return data;
    }
}
