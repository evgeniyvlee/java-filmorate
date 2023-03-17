package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTests {

    private final UserDbStorage userStorage;
    private final UserDao userDao;
    private final List<User> users = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        User userOne = new User();
        userOne.setId(1L);
        userOne.setName("Ivan Ivanov");
        userOne.setLogin("ivanov");
        userOne.setEmail("ivanov@mail.ru");
        userOne.setBirthday(LocalDate.of(1990, 8, 20));

        User userTwo = new User();
        userTwo.setId(2L);
        userTwo.setName("Petr Petrov");
        userTwo.setLogin("petrov");
        userTwo.setEmail("petrov@mail.ru");
        userTwo.setBirthday(LocalDate.of(1994, 3, 14));

        User friend = new User();
        friend.setId(3L);
        friend.setName("Common Friend");
        friend.setLogin("friend");
        friend.setEmail("friend@mail.ru");
        friend.setBirthday(LocalDate.of(2000, 1, 5));


        users.add(userOne);
        users.add(userTwo);
        users.add(friend);
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testGetUserById() {
        User dbUser = userStorage.get(1L);

        Assertions.assertEquals(users.get(0).getName(), dbUser.getName());
        Assertions.assertEquals(users.get(0).getLogin(), dbUser.getLogin());
        Assertions.assertEquals(users.get(0).getEmail(), dbUser.getEmail());
        Assertions.assertEquals(users.get(0).getBirthday(), dbUser.getBirthday());
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testDeleteUserById() {
        userStorage.delete(1L);
        Assertions.assertThrows(DataNotFoundException.class, () -> {userStorage.get(1L);});
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testUpdateUserById() {
        User user = users.get(1);
        user.setEmail("qwerty@yandex.ru");
        userStorage.update(user);

        User dbUser = userStorage.get(2L);
        Assertions.assertEquals(user.getName(), dbUser.getName());
        Assertions.assertEquals(user.getLogin(), dbUser.getLogin());
        Assertions.assertEquals(user.getEmail(), dbUser.getEmail());
        Assertions.assertEquals(user.getBirthday(), dbUser.getBirthday());
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testGetAll() {
        List<User> dbList = userStorage.getAll();
        Assertions.assertEquals(users.size(), dbList.size());
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testAddAndGetFriend() {
        userDao.addFriend(users.get(0).getId(), users.get(2).getId());
        List<User> friends = userDao.getFriends(users.get(0).getId());
        Assertions.assertEquals(users.get(2).getName(), friends.get(0).getName());
        Assertions.assertEquals(users.get(2).getLogin(), friends.get(0).getLogin());
        Assertions.assertEquals(users.get(2).getEmail(), friends.get(0).getEmail());
        Assertions.assertEquals(users.get(2).getBirthday(), friends.get(0).getBirthday());

    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testDeleteFriend() {
        userDao.removeFriend(users.get(0).getId(), users.get(2).getId());
        List<User> friends = userDao.getFriends(users.get(0).getId());

        Assertions.assertEquals(0, friends.size());
    }

    @Test
    @Sql({"/schema.sql", "/create_test_data.sql"})
    public void testGetCommonFriends() {
        List<User> commonFriends = userDao.getCommonFriends(users.get(0).getId(), users.get(1).getId());
        Assertions.assertEquals(users.get(2).getName(), commonFriends.get(0).getName());
        Assertions.assertEquals(users.get(2).getLogin(), commonFriends.get(0).getLogin());
        Assertions.assertEquals(users.get(2).getEmail(), commonFriends.get(0).getEmail());
        Assertions.assertEquals(users.get(2).getBirthday(), commonFriends.get(0).getBirthday());
    }
}
