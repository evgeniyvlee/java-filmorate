package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.filmorate.exception.DataAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller provides methods for creating, updating and getting films from storage.
 * @author Evgeniy Lee
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController implements Controller<Film> {
    // Film service
    private final FilmService service;

    /**
     * Constructor
     * @param service film service instance
     */
    @Autowired
    public FilmController(final FilmService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public List<Film> getAll() {
        log.info(LoggingMessages.GET.toString());
        return service.getAll();
    }

    @Override
    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws DataAlreadyExistException {
        log.info(LoggingMessages.CREATE.toString(), film);
        return service.create(film);
    }

    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws DataNotFoundException {
        log.info(LoggingMessages.UPDATE.toString(), film);
        return service.update(film);
    }

    @Override
    @GetMapping("{id}")
    public Film get(@PathVariable long id) throws DataNotFoundException {
        return service.get(id);
    }

    @Override
    @DeleteMapping
    public void delete(@PathVariable long id) throws DataNotFoundException {
        service.delete(id);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) throws DataNotFoundException {
        service.addLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) throws DataNotFoundException {
        service.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") int count) {
        return service.getPopular(count);
    }
}
