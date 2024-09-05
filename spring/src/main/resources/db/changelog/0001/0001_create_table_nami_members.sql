--liquibase formatted sql
--changeset doktorlenz:#init

CREATE TABLE nami_members
(
    member_id     int primary key,
    firstname     varchar(50) not null,
    lastname      varchar(50) not null,
    date_of_birth date        not null,
    rank          varchar(50) null,
    gender        varchar(50) null
);