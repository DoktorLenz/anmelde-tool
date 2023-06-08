--liquibase formatted sql
--changeset doktorlenz:#45

drop type if exists roles;
create type role as enum (
    'VERIFIED',
    'OTHER'
    );