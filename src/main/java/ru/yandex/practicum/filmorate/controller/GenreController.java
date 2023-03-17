package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.Service;
import java.util.List;

/**
 * Controller provides methods for getting genres.
 * @author Evgeniy Lee
 */
@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {
    // Genre service
    private final Service<Genre> service;

    /**
     * Constructor
     * @param service genre service
     */
    public GenreController(final Service<Genre> service) {
        this.service = service;
    }

    /**
     * Get list of genres
     * @return list of genres
     */
    @GetMapping
    public List<Genre> getAll() {
        log.info(LoggingMessages.GET.toString());
        return service.getAll();
    }

    /**
     * Get genre by its id
     * @param id genre id
     * @return genre instance
     */
    @GetMapping("{id}")
    public Genre get(@PathVariable long id) {
        log.info(LoggingMessages.GET_BY_ID.toString(), id);
        return service.get(id);
    }
}
