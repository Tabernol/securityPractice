CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    login VARCHAR(64) NOT NULL UNIQUE ,
    birth_date DATE,
    name VARCHAR(64),
    role VARCHAR(32)
    );
