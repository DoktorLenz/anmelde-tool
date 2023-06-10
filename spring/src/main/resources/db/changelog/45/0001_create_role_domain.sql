--liquibase formatted sql
--changeset doktorlenz:#45

CREATE DOMAIN _roles_array AS TEXT[]
    CHECK (VALUE <@ ARRAY ['VERIFIED', 'ADMIN']);