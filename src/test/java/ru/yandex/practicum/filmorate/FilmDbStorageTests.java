package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTests {
    private final FilmDbStorage filmStorage;
    private final FilmDao filmDao;
    private final List<Film> films = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        Film filmOne = new Film();
        Mpa mpaOne = new Mpa();
        filmOne.setId(1L);
        filmOne.setName("nisi eiusmod");
        filmOne.setDescription("adipisicing");
        filmOne.setReleaseDate(LocalDate.of(1967, 3, 25));
        filmOne.setDuration(100);
        mpaOne.setId(1L);
        mpaOne.setName("G");
        filmOne.setMpa(mpaOne);

        Film filmTwo = new Film();
        Mpa mpaTwo = new Mpa();
        filmTwo.setId(2L);
        filmTwo.setName("New film");
        filmTwo.setDescription("New film about friends");
        filmTwo.setReleaseDate(LocalDate.of(1999, 4, 30));
        filmTwo.setDuration(120);
        mpaTwo.setId(3L);
        mpaTwo.setName("PG-13");
        filmTwo.setMpa(mpaTwo);

        films.add(filmOne);
        films.add(filmTwo);
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testGetFilmById() {
        Film dbFilm = filmStorage.get(1L);

        Assertions.assertEquals(films.get(0).getId(), dbFilm.getId());
        Assertions.assertEquals(films.get(0).getName(), dbFilm.getName());
        Assertions.assertEquals(films.get(0).getDescription(), dbFilm.getDescription());
        Assertions.assertEquals(films.get(0).getReleaseDate(), dbFilm.getReleaseDate());
        Assertions.assertEquals(films.get(0).getDuration(), dbFilm.getDuration());
        Assertions.assertEquals(films.get(0).getMpa().getName(), dbFilm.getMpa().getName());
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testDeleteFilmById() {
        filmStorage.delete(films.get(0).getId());
        Assertions.assertThrows(DataNotFoundException.class, () -> {filmStorage.get(films.get(0).getId());});
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testUpdateFilmById() {
        Film film = films.get(0);
        film.setName("Film updated");

        filmStorage.update(film);
        Film dbFilm = filmStorage.get(film.getId());
        Assertions.assertEquals(film.getId(), dbFilm.getId());
        Assertions.assertEquals(film.getName(), dbFilm.getName());
        Assertions.assertEquals(film.getDescription(), dbFilm.getDescription());
        Assertions.assertEquals(film.getReleaseDate(), dbFilm.getReleaseDate());
        Assertions.assertEquals(film.getDuration(), dbFilm.getDuration());
        Assertions.assertEquals(film.getMpa().getName(), dbFilm.getMpa().getName());
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testGetAll() {
        List<Film> dbList = filmStorage.getAll();
        Assertions.assertEquals(films.size(), dbList.size());
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testAddRemoveLikeAndGetPopular() {
        filmDao.addLike(films.get(0).getId(), 1L);
        filmDao.addLike(films.get(0).getId(), 2L);
        filmDao.addLike(films.get(1).getId(), 3L);
        List<Film> dbFilms = filmDao.getPopular(1);

        Assertions.assertEquals(films.get(0).getId(), dbFilms.get(0).getId());
        Assertions.assertEquals(films.get(0).getName(), dbFilms.get(0).getName());
        Assertions.assertEquals(films.get(0).getDescription(), dbFilms.get(0).getDescription());
        Assertions.assertEquals(films.get(0).getReleaseDate(), dbFilms.get(0).getReleaseDate());
        Assertions.assertEquals(films.get(0).getDuration(), dbFilms.get(0).getDuration());
        Assertions.assertEquals(films.get(0).getMpa().getName(), dbFilms.get(0).getMpa().getName());

        filmDao.removeLike(films.get(0).getId(), 1L);
        filmDao.removeLike(films.get(0).getId(), 2L);
        dbFilms = filmDao.getPopular(1);
        Assertions.assertEquals(films.get(1).getId(), dbFilms.get(0).getId());
        Assertions.assertEquals(films.get(1).getName(), dbFilms.get(0).getName());
        Assertions.assertEquals(films.get(1).getDescription(), dbFilms.get(0).getDescription());
        Assertions.assertEquals(films.get(1).getReleaseDate(), dbFilms.get(0).getReleaseDate());
        Assertions.assertEquals(films.get(1).getDuration(), dbFilms.get(0).getDuration());
        Assertions.assertEquals(films.get(1).getMpa().getName(), dbFilms.get(0).getMpa().getName());
    }
}
