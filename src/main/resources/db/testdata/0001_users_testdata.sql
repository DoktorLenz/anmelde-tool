--liquibase formatted sql
--changeset stinner:init runOnChange:true


delete
from users_;

insert into users_ (id, email, password, account_locked, credentials_expired, enabled, authorities, firstname, lastname)
values ('8a01516e-a70a-4613-9972-6168c1704795', 'admin@dpsgkolbermoor.de',
        '$2a$10$SALfL1nHCsctCL/RGvMdze76XbVBskDyXbWIGxMYSwD5enA3ToaUi', false, false, true,
        ARRAY ['ROLE_USER', 'ROLE_ADMIN'], 'admin', 'admin');