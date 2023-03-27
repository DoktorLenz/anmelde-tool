--liquibase formatted sql
--changeset stinner:init

create table reset_passwords
(
    reset_id   uuid primary key,
    user_id    uuid        not null,
    created_at timestamptz not null,
    email_sent timestamptz null,
    foreign key (user_id) references users_ (id)
);