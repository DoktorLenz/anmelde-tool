--liquibase formatted sql
--changeset doktorlenz:#init

CREATE TABLE iam_users
(
    subject   varchar(100) PRIMARY KEY,
    firstname varchar(50),
    lastname  varchar(50),
    username  varchar(50),
    email     varchar(255)
);