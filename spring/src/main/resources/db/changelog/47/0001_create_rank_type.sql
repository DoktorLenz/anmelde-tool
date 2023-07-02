--liquibase formatted sql
--changeset doktorlenz:#47

DROP TYPE IF EXISTS rank;
CREATE TYPE rank AS ENUM (
    'UNDEFINED',
    'WOELFLING',
    'JUNGPFADFINDER',
    'PFADFINDER',
    'ROVER'
    );