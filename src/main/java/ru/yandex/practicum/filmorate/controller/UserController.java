package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {

    @GetMapping
    public List<User> getAll() {
        log.info("Getting all users");
        return super.getAll();
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        log.info("Creating user {}", user);
        User createdUser = null;
        try {
            createdUser = super.create(user);
        } catch (ValidationException e) {
            log.error(e.getMessage());
            throw e;
        }
        return createdUser;
    }

    @PutMapping
    public User update(@RequestBody User user) throws ValidationException {
        log.info("Updating user {}", user);
        User updatedUser = null;
        try {
            updatedUser = super.update(user);
        } catch (ValidationException e) {
            log.error(e.getMessage());
            throw e;
        }
        return updatedUser;
    }
}
