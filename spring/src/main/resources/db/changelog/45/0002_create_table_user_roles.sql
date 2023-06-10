--liquibase formatted sql
--changeset doktorlenz:#45

CREATE TABLE user_roles
(
    -- subject is jwt subject
    subject VARCHAR(100) PRIMARY KEY,
    roles   _roles_array
);