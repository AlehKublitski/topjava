DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2019-12-12 10:10', 'ланч', 500, 100000),
       ('2019-12-12 14:10', 'обед', 1000, 100000),
       ('2019-12-12 02:10', 'ужин', 500, 100000);
