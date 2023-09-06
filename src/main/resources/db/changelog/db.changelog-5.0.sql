--liquibase formatted sql

--changeset max:1
CREATE TABLE password_reset_token
(
    id          BIGSERIAL PRIMARY KEY,
    token       VARCHAR(255) NOT NULL,
    user_id     BIGSERIAL    NOT NULL,
    expiry_date TIMESTAMP    NOT NULL
);