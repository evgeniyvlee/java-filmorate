package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DBUtils {
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Film makeFilm(final ResultSet rs, final long rowNum) throws SQLException {
        final Film film = new Film();
        final Mpa mpa = new Mpa();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(LocalDate.parse(rs.getString("releasedate"), FORMATTER));
        film.setDuration(rs.getLong("duration"));
        mpa.setId(rs.getLong("mpa_id"));
        mpa.setName(rs.getString("mpa_name"));
        film.setMpa(mpa);
        return film;
    }

    public static User makeUser(final ResultSet rs, final long rowNum) throws SQLException {
        final User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setLogin(rs.getString("login"));
        user.setEmail(rs.getString("email"));
        user.setBirthday(LocalDate.parse(rs.getString("birthday"), FORMATTER));
        return user;
    }

    public static Mpa makeMpa(final ResultSet rs, final long rowNum) throws SQLException {
        final Mpa mpa = new Mpa();
        mpa.setId(rs.getLong("mpa_id"));
        mpa.setName(rs.getString("mpa_name"));
        return mpa;
    }

    public static Genre makeGenre(final ResultSet rs, final long rowNum) throws SQLException {
        final Genre genre = new Genre();
        genre.setId(rs.getLong("genre_id"));
        genre.setName(rs.getString("genre_name"));
        return genre;
    }
}
