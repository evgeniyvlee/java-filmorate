package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.Service;
import java.util.List;

/**
 * Controller provides methods for getting mpa.
 * @author Evgeniy Lee
 */
@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {
    // Mpa controller
    private final Service<Mpa> service;

    /**
     * Constructor
     * @param service MPA service
     */
    public MpaController(final Service<Mpa> service) {
        this.service = service;
    }

    /**
     * Get list of mpa
     * @return list of mpa
     */
    @GetMapping
    public List<Mpa> getAll() {
        log.info(LoggingMessages.GET.toString());
        return service.getAll();
    }

    /**
     * Get mpa by its id
     * @param id mpa id
     * @return mpa instance
     */
    @GetMapping("{id}")
    public Mpa get(@PathVariable long id) {
        log.info(LoggingMessages.GET_BY_ID.toString(), id);
        return service.get(id);
    }
}
