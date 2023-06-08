--liquibase formatted sql
--changeset doktorlenz:#45 runOnChange:true

delete
from user_roles;

insert into "user_roles" (subject, roles)
values ('215538795716542471', ['VERIFIED', 'ADMIN']);