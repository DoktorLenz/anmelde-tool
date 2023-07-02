--liquibase formatted sql
--changeset doktorlenz:#47

DROP TYPE IF EXISTS gender;
CREATE TYPE gender AS ENUM (
    'MALE',
    'FEMALE',
    'DIVERSE',
    'UNDEFINED'
    );