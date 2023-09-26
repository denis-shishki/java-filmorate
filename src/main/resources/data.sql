INSERT INTO PUBLIC.GENRE (NAME)
SELECT 'Комедия' UNION ALL
SELECT 'Драма' UNION ALL
SELECT 'Мультфильм' UNION ALL
SELECT 'Триллер' UNION ALL
SELECT 'Документальный' UNION ALL
SELECT 'Боевик'
WHERE NOT EXISTS (SELECT * FROM PUBLIC.GENRE);

INSERT INTO PUBLIC.MPA (name)
SELECT 'G' UNION ALL
SELECT 'PG' UNION ALL
SELECT 'PG-13' UNION ALL
SELECT 'R' UNION ALL
SELECT 'NC-17'
WHERE NOT EXISTS (SELECT * FROM PUBLIC.MPA);

INSERT INTO PUBLIC.USERS (name, login, email, birthday)
VALUES ('Имя','Логин','почта@точка.ру','2000-05-05'),
       ('Имя2','Логин2','почта2@точка.ру','1999-05-05'),
       ('Имя3','Логин3','почта3@точка.ру','1998-05-05'),
       ('Имя4','Логин4','почта4@точка.ру','1997-05-05');


INSERT INTO PUBLIC.FILMS (name, description, release_date, duration, mpa_id)
VALUES ('Фильм', 'Описание фильма','2000-10-10', 120, 3),
       ('Фильм2', 'Описание фильма2','2000-11-10', 200, 1),
       ('Фильм3', 'Описание фильма3','2000-12-10', 60, 2);

INSERT INTO PUBLIC.FRIENDS_LIST (user_id, friend_id, confirmation)
VALUES (1, 2, 't'),
       (2, 1, 't'),
       (2, 3, 'f');


