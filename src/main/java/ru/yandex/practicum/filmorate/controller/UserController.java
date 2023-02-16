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
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {

    @Override
    @GetMapping
    public List<User> getAll() {
        log.info(LoggingMessages.GET.toString());
        return super.getAll();
    }

    @Override
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info(LoggingMessages.CREATE.toString(), user);
        return super.create(user);
    }

    @Override
    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        log.info(LoggingMessages.UPDATE.toString(), user);
        return super.update(user);
    }
}
