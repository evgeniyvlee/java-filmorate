package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.Service;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController implements Controller<Genre> {

    private final Service<Genre> service;

    public GenreController(final Service<Genre> service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public List<Genre> getAll() {
        log.info(LoggingMessages.GET.toString());
        return service.getAll();
    }

    @Override
    @PostMapping
    public Genre create(@Valid @RequestBody Genre genre) {
        log.info(LoggingMessages.CREATE.toString(), genre);
        return service.create(genre);
    }

    @Override
    @PutMapping
    public Genre update(@Valid @RequestBody Genre genre) {
        log.info(LoggingMessages.UPDATE.toString(), genre);
        return service.update(genre);
    }

    @Override
    @GetMapping("{id}")
    public Genre get(@PathVariable long id) {
        log.info(LoggingMessages.GET_BY_ID.toString(), id);
        return service.get(id);
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        log.info(LoggingMessages.DELETE_BY_ID.toString(), id);
        service.delete(id);
    }
}
