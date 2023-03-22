--liquibase formatted sql
--changeset stinner:init runOnChange:true

delete
from registrations;

insert into registrations (registration_id, firstname, lastname, email, created_at, email_sent)
values ('f5996532-f895-4e6b-bab3-0921d6ee8015', 'Peter', 'Müller', 'laurenz.stinner@ymail.com',
        '2021-09-10 21:10:00.000000+00', '2021-09-10 21:10:00.000000+00')