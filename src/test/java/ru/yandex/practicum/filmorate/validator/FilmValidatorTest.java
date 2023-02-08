package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.impl.FilmValidator;
import java.time.LocalDate;

public class FilmValidatorTest {
    private Film film;
    private final Validator<Film> validator = new FilmValidator();

    @BeforeEach
    void beforeEach() {
        film = new Film(
    "Casino Royale",
"Special Agent James Bond embarks on a mission to " +
                "prevent Le Chiffre, a mob banker, from winning a high" +
                " stakes poker game. He is aided by Vesper Lynd, a British Treasury agent.",
            LocalDate.of(2006, 11, 16),
    144
        );
    }

    @Test
    void validateNullName() {
        film.setName(null);
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(film));
        String expectedMessage = "Film name is NULL";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Name is not NULL");
    }

    @Test
    void validateBlankName() {
        film.setName("");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(film));
        String expectedMessage = "Film name is blank";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Name is not blank");
    }

    @Test
    void validateMaxLengthDescription() {
        film.setDescription("Casino Royale is a 2006 spy film, the twenty-first in the Eon Productions " +
                "James Bond series, and the third screen adaptation of Ian Fleming's 1953 novel of the same " +
                "name. Directed by Martin Campbell from a screenplay by Neil Purvis, Robert Wade, and Paul Haggis, " +
                "it stars Daniel Craig in his first appearance as Bond, alongside Eva Green, Mads Mikkelsen, " +
                "Judi Dench, and Jeffrey Wright. In the film, Bond is on assignment to bankrupt terrorist financier " +
                "Le Chiffre (Mikkelsen) in a high-stakes poker game at the Casino Royale in Montenegro.\n" +
                "\n" +
                "Following Die Another Day (2002), Eon decided to reboot the franchise,[4][5] attempting to " +
                "counteract perceived unrealistic elements of previous entries and instead explore a less experienced, " +
                "more vulnerable Bond.[6] Casting involved a widespread search for a new actor to succeed Pierce " +
                "Brosnan as Bond; the choice of Craig, announced in October 2005, proved controversial. " +
                "Principal photography took place in the Bahamas, Italy, the United Kingdom, and the Czech " +
                "Republic, with interior sets built at Pinewood Studios and Barrandov Studios. Casino Royale " +
                "features primarily practical stuntwork as opposed to the computer-generated placements " +
                "seen in other Bond films."
        );
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(film));
        String expectedMessage = "Description length more than 200 symbols";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Description length is not more than 200 symbols");
    }

    @Test
    void validateReleaseDate() {
        film.setReleaseDate(LocalDate.of(1812, 11, 16));
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(film));
        String expectedMessage = "Release date is earlier 1895-12-28";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Release date is later 1895-12-28");
    }

    @Test
    void validateDuration() {
        film.setDuration(-144);
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(film));
        String expectedMessage = "Duration is negative";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Duration is positive");
    }
}
