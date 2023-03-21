--liquibase formatted sql
--changeset stinner:init

drop type if exists authority;
create type authority as enum (
    'ROLE_USER',
    'ROLE_ADMIN'
    )