--liquibase formatted sql
--changeset doktorlenz:#45

create table user_roles
(
    -- subject is jwt subject
    subject varchar(100) primary key,
    roles   role[] not null
);