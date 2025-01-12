--liquibase formatted sql
--changeset doktorlenz:#init

CREATE TABLE iam_users_nami_members_mapping
(
    subject   varchar(100),
    member_id int,
    PRIMARY KEY (subject, member_id),
    FOREIGN KEY (subject) REFERENCES iam_users (subject) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES nami_members (member_id) ON DELETE CASCADE
);