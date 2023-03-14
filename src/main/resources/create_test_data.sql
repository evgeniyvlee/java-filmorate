INSERT INTO users (name, login, email, birthday) VALUES ('Ivan Ivanov', 'ivanov', 'ivanov@mail.ru', '1990-08-20');
INSERT INTO users (name, login, email, birthday) VALUES ('Petr Petrov', 'petrov', 'petrov@mail.ru', '1994-03-14');
INSERT INTO users (name, login, email, birthday) VALUES ('Common Friend', 'friend', 'friend@mail.ru', '2000-01-05');

INSERT INTO friendship (user_id, friend_id) VALUES (1, 3);
INSERT INTO friendship (user_id, friend_id) VALUES (2, 3);

MERGE INTO mpa
    KEY(id)
VALUES  (1, 'G'),
        (2, 'PG'),
        (3, 'PG-13'),
        (4, 'R'),
        (5, 'NC-17');

MERGE INTO genre
    KEY(id)
VALUES  (1, 'Комедия'),
        (2, 'Драма'),
        (3, 'Мультфильм'),
        (4, 'Триллер'),
        (5, 'Документальный'),
        (6, 'Боевик');

INSERT INTO films (name, description, releasedate, duration, mpa_id) VALUES ('nisi eiusmod', 'adipisicing', '1967-03-25', 100, 1);
INSERT INTO films (name, description, releasedate, duration, mpa_id) VALUES ('New film', 'New film about friends', '1999-04-30', 120, 3);