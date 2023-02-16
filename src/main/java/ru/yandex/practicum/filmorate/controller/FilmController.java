package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller provides methods for creating, updating and getting films from storage.
 * @author Evgeniy Lee
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {

    @Override
    @GetMapping
    public List<Film> getAll() {
        log.info(LoggingMessages.GET.toString());
        return super.getAll();
    }

    @Override
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info(LoggingMessages.CREATE.toString(), film);
        return super.create(film);
    }

    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        log.info(LoggingMessages.UPDATE.toString(), film);
        return super.update(film);
    }
}
