--liquibase formatted sql

--changeset max:1
ALTER TABLE users
    ADD COLUMN password varchar(128) default '{noop}root';

--changeset max:2
ALTER TABLE users_aud
    ADD COLUMN password varchar(128);