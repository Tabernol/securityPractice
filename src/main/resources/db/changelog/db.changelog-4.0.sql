-- --liquibase formatted sql
--
-- --changeset max:1
-- CREATE TABLE IF NOT EXISTS city (
--     id BIGSERIAL PRIMARY KEY,
--     name VARCHAR(64) NOT NULL,
--     lat NUMERIC NOT NULL,
--     lng NUMERIC NOT NULL,
--     country VARCHAR(64),
--     iso2 VARCHAR(2),
--     admin_name VARCHAR(64),
--     capital VARCHAR(64),
--     population INTEGER,
--     population_proper INTEGER
-- );
