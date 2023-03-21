--liquibase formatted sql
--changeset stinner:init

create table registrations
(
    registration_id uuid primary key,
    firstname       varchar(255) not null,
    lastname        varchar(255) not null,
    email           varchar(255) not null,
    created_at      timestamptz  not null
);