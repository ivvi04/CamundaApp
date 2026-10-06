-- liquibase formatted sql

-- changeset Lakeev-DA:baseline-1
CREATE TABLE IF NOT EXISTS camunda_app.users
(
    id        bigserial primary key         not null,
    fio       text                          not null,
    birthday  date                          not null
);
-- rollback DROP TABLE IF EXISTS camunda_app.users;

-- changeset Lakeev-DA:baseline-2
CREATE TABLE IF NOT EXISTS camunda_app.tickets
(
    id
    bigserial
    primary
    key
    not
    null,
    name
    text
    not
    null,
    create_at
    timestamp
    not
    null,
    status
    varchar
    not
    null,
    user_id
    bigint
    references
    camunda_app
    .
    users
(
    id
) not null
);
-- rollback DROP TABLE IF EXISTS camunda_app.tickets;

-- changeset Lakeev-DA:baseline-3
ALTER TABLE IF EXISTS camunda_app.users ADD COLUMN IF NOT EXISTS email text UNIQUE;
-- rollback ALTER TABLE IF EXISTS camunda_app.users DROP COLUMN IF EXISTS email;