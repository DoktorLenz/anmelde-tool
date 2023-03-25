--liquibase formatted sql
--changeset stinner:init runOnChange:true


delete
from users_;

insert into users_ (id, email, password, account_locked, credentials_expired, enabled, authorities, created_at,
                    firstname, lastname)
values ('8a01516e-a70a-4613-9972-6168c1704795', 'laurenz.stinner2@gmail.com',
        '$2a$10$xXztkzVsBXuzQ66aF6flxeJrsp.RwzDZKwf5ldngAJAHKRbT02qsi', false, false, true,
        ARRAY ['ROLE_USER'::authority, 'ROLE_ADMIN'::authority], '2021-09-10 21:10:00.000000+00', 'admin', 'admin');