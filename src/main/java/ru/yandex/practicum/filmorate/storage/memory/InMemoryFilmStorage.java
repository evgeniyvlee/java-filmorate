package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DataStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

/**
 * Film storage in memory
 * @author Evgeniy Lee
 */
@Component
public class InMemoryFilmStorage extends DataStorage<Film> implements FilmStorage {
}
