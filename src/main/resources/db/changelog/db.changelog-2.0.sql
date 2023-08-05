--liquibase formatted sql

--changeset max:1
ALTER TABLE users
    ADD COLUMN created_at TIMESTAMP;
--changeset max:2
ALTER TABLE users
    ADD COLUMN modified_at TIMESTAMP;
--changeset max:3
ALTER TABLE users
    ADD COLUMN created_by VARCHAR(32);
--changeset max:4
ALTER TABLE users
    ADD COLUMN modified_by VARCHAR(32);


--changeset max:5
CREATE TABLE IF NOT EXISTS revision
(
    id SERIAL PRIMARY KEY ,
    timestamp BIGINT NOT NULL
);

--changeset max:6
CREATE TABLE IF NOT EXISTS users_aud
(
    id BIGINT,
    rev INT REFERENCES revision (id),
    revtype SMALLINT ,
    login VARCHAR(64),
    name VARCHAR(64),
    birth_date DATE,
    role VARCHAR(32)
    );