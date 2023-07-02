--liquibase formatted sql
--changeset doktorlenz:#47

CREATE TABLE nami_members
(
    member_id     int primary key,
    firstname     varchar(50) not null,
    lastname      varchar(50) not null,
    date_of_birth date        not null,
    rank          rank        null,
    gender        gender      null
);