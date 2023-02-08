package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

/**
 * Controller provides methods for creating, updating and getting films from storage.
 * @author Evgeniy Lee
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    /**
     * Get all films from storage
     * @return list of data
     */
    @GetMapping
    public List<Film> getAll() {
        log.info("Getting all films");
        return super.getAll();
    }

    /**
     * Create film data instance in storage
     * @param film film instance
     * @return created film
     * @throws ValidationException if validation error occurs
     */
    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        log.info("Creating film {}", film);
        Film createdFilm = null;
        try {
            createdFilm = super.create(film);
        } catch (ValidationException e) {
            log.error(e.getMessage());
            throw e;
        }
        return createdFilm;
    }

    /**
     * Update film instance in storage
     * @param film film instance
     * @return updated film
     * @throws ValidationException if validation error occurs
     */
    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        log.info("Updating film {}", film);
        Film updatedFilm = null;
        try {
            updatedFilm = super.update(film);
        } catch (ValidationException e) {
            log.error(e.getMessage());
            throw e;
        }
        return updatedFilm;
    }
}
