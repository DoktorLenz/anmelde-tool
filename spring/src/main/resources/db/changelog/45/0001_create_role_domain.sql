--liquibase formatted sql
--changeset doktorlenz:#45

DROP DOMAIN IF EXISTS _roles_array;
CREATE DOMAIN _roles_array AS TEXT[]
    CHECK (VALUE <@ ARRAY ['VERIFIED', 'ADMIN']);