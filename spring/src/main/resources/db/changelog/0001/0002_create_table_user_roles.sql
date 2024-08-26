--liquibase formatted sql
--changeset doktorlenz:#init

CREATE TABLE user_roles
(
    -- subject is jwt subject
    subject VARCHAR(100) PRIMARY KEY,
    roles   _roles_array
);