--liquibase formatted sql

--changeset max:1
COPY city(name,lat,lng,country,iso2,admin_name,capital,population,population_proper)
    FROM 'C:\Education\testFiles\ua.csv'
    DELIMITER ','
    CSV HEADER;