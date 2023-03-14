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
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.Service;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController implements Controller<Mpa> {

    private final Service<Mpa> service;

    public MpaController(final Service<Mpa> service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public List<Mpa> getAll() {
        log.info(LoggingMessages.GET.toString());
        return service.getAll();
    }

    @Override
    @PostMapping
    public Mpa create(@Valid @RequestBody Mpa mpa) {
        log.info(LoggingMessages.CREATE.toString(), mpa);
        return service.create(mpa);
    }

    @Override
    @PutMapping
    public Mpa update(@Valid @RequestBody Mpa mpa) {
        log.info(LoggingMessages.UPDATE.toString(), mpa);
        return service.update(mpa);
    }

    @Override
    @GetMapping("{id}")
    public Mpa get(@PathVariable long id) {
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
