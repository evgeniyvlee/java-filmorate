package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.storage.Storage;

@Service
public class GenreServiceImpl extends AbstractService<Genre> {
    /**
     * Constructor
     *
     * @param storage storage instance
     */
    protected GenreServiceImpl(final Storage<Genre> storage) {
        super(storage);
    }
}
