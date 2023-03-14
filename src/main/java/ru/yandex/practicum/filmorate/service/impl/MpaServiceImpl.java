package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.storage.Storage;

@org.springframework.stereotype.Service
public class MpaServiceImpl extends AbstractService<Mpa> {

    public MpaServiceImpl(@Qualifier("dbMpaStorage") final Storage<Mpa> storage) {
        super(storage);
    }
}
