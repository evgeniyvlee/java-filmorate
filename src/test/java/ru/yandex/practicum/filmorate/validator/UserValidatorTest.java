package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.impl.UserValidator;
import java.time.LocalDate;

public class UserValidatorTest {
    private User user;
    private final Validator<User> validator = new UserValidator();

    @BeforeEach
    void beforeEach() {
        user = new User(
            "johnnylee@yandex.ru",
            "johnnylee",
            "Evgeniy Lee",
            LocalDate.of(1987, 2, 7)
        );
    }

    @Test
    void validateNullEmail() {
        user.setEmail(null);
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(user));
        String expectedMessage = "Email is NULL";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Email is not NULL");
    }

    @Test
    void validateBlankEmail() {
        user.setEmail("");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(user));
        String expectedMessage = "Email is blank";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Email is not blank");
    }

    @Test
    void validateInvalidFormatEmail() {
        user.setEmail("johnnylee_yandexru");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(user));
        String expectedMessage = "Invalid email format";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Email is not valid");
    }

    @Test
    void validateNullLogin() {
        user.setLogin(null);
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(user));
        String expectedMessage = "Login is NULL";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Login is not NULL");
    }

    @Test
    void validateBlankLogin() {
        user.setLogin("");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(user));
        String expectedMessage = "Login is blank";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Login is not blank");
    }

    @Test
    void validateLoginWithSpaces() {
        user.setLogin("johnny lee");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(user));
        String expectedMessage = "Login contains spaces";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Login has spaces");
    }

    @Test
    void validateNullBirthday() {
        user.setBirthday(null);
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(user));
        String expectedMessage = "Birthday is NULL";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Birthday is not NULL");
    }

    @Test
    void validateBirthdayInFuture() {
        user.setBirthday(LocalDate.of(2087, 2, 7));
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(user));
        String expectedMessage = "Birthday in future";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Birthday is not in future");
    }
}
