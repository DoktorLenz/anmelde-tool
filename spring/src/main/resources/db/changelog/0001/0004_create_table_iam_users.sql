--liquibase formatted sql
--changeset doktorlenz:#init

CREATE TABLE iam_users
(
    subject   varchar(100) not null,
    firstname varchar(50),
    lastname  varchar(50),
    username  varchar(50),
    email     varchar(255)
);