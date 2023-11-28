--liquibase formatted sql

--changeset max:1
INSERT INTO users (birth_date, name, role, login)
VALUES ('1990-01-10', 'admin',  'ADMIN', 'admin@gmail.com'),
       ('1995-10-19', 'user',  'USER', 'user@gmail.com'),
       ('2001-12-23', 'John',  'USER', 'john@gmail.com'),
       ('1984-03-14', 'Tom',  'USER', 'tom@gmail.com'),
       ('1984-03-14', 'ADMIN2',  'ADMIN', 'admin2@gmail.com')