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
    username VARCHAR(64) NOT NULL UNIQUE ,
    birth_date DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    role VARCHAR(32),
    company_id INT
    );