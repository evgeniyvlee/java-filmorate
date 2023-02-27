package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.DataAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Data;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.List;

/**
 * Service for creating, updating and getting data.
 * @param <T> data type
 * @author Evgeniy Lee
 */
@Slf4j
public abstract class AbstractService<T extends Data> implements Service<T> {
    // Data storage
    protected final Storage<T> storage;
    // IDs increment every time when creating data
    private Long ids = 0L;

    /**
     * Constructor
     * @param storage storage instance
     */
    protected AbstractService(final Storage<T> storage) {
        this.storage = storage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll() {
        final List<T> listData = storage.getAll();
        log.info(LoggingMessages.GET_ALL_DATA.toString(), listData);
        return listData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(final T data) {
        data.setId(++ids);
        storage.create(data);
        log.info(LoggingMessages.SUCCESSFUL_CREATE.toString(), data);
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(final T data) {
        storage.update(data);
        log.info(LoggingMessages.SUCCESSFUL_UPDATE.toString(), data);
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(final long id) {
        final T data = storage.get(id);
        log.info(LoggingMessages.GET_DATA_BY_ID.toString(), data);
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final long id) {
        storage.delete(id);
        log.info(LoggingMessages.DELETE_DATA_BY_ID.toString(), id);
    }
}
