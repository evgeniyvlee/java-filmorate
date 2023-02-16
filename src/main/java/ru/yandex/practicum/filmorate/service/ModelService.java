package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.messages.ExceptionMessages;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Data;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static java.util.stream.Collectors.toList;

/**
 * Service for creating, updating and getting data.
 * @param <T> data type
 * @author Evgeniy Lee
 */
@Slf4j
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
        List<T> listData = storage.values().stream().collect(toList());
        log.info(LoggingMessages.GET_ALL_DATA.toString(), listData);
        return Collections.unmodifiableList(listData);
    }

    /**
     * Create data and save in storage
     * @param data data
     * @return created data
     */
    public T create(T data) {
        data.setId(++ids);
        storage.put(data.getId(), data);
        log.info(LoggingMessages.SUCCESSFUL_CREATE.toString(), data);
        return data;
    }

    /**
     * Update data in storage
     * @param data data
     * @return updated data
     */
    public T update(T data) throws ValidationException {
        if (storage.get(data.getId()) == null) {
            log.error(LoggingMessages.NO_FOUND.toString(), data);
            throw new ValidationException(ExceptionMessages.DATA_NOT_FOUND);
        }
        storage.put(data.getId(), data);
        log.info(LoggingMessages.SUCCESSFUL_UPDATE.toString(), data);
        return data;
    }
}
