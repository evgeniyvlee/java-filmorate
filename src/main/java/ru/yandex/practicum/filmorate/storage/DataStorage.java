package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.DataAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.messages.ExceptionMessages;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Data;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Data storage provides CRUD operations for any data
 * @param <T> data type
 * @author Evgeniy Lee
 */
@Slf4j
public abstract class DataStorage<T extends Data> implements Storage<T> {
    // Map holding ids and data
    private final Map<Long, T> storage = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(T data) throws DataAlreadyExistException {
        final long id = data.getId();
        if (storage.get(id) != null) {
            log.error(LoggingMessages.DATA_ALREADY_EXIST.toString(), data);
            throw new DataAlreadyExistException(ExceptionMessages.DATA_ALREADY_EXIST);
        }
        storage.put(id, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(long id) throws DataNotFoundException {
        final T data = storage.get(id);
        if (data == null) {
            log.error(LoggingMessages.ID_NOT_FOUND.toString(), id);
            throw new DataNotFoundException(ExceptionMessages.DATA_NOT_FOUND);
        }
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(T data) throws DataNotFoundException {
        final long id = data.getId();
        if (storage.get(id) == null) {
            log.error(LoggingMessages.ID_NOT_FOUND.toString(), id);
            throw new DataNotFoundException(ExceptionMessages.DATA_NOT_FOUND);
        }
        storage.put(id, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long id) throws DataNotFoundException {
        if (storage.get(id) == null) {
            log.error(LoggingMessages.ID_NOT_FOUND.toString(), id);
            throw new DataNotFoundException(ExceptionMessages.DATA_NOT_FOUND);
        }
        storage.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll() {
        List<T> listData = storage.values().stream().collect(toList());
        return Collections.unmodifiableList(listData);
    }
}
