-- liquibase formatted sql

-- changeset Lakeev-DA:baseline-1
CREATE TABLE IF NOT EXISTS camunda_app.users
(
    id          bigserial   not null primary key,
    fio         text        not null,
    birthday    date        not null
);
-- rollback DROP TABLE IF EXISTS camunda_app.users;

-- changeset Lakeev-DA:baseline-2
CREATE TABLE IF NOT EXISTS camunda_app.tickets
(
    id          bigserial   not null primary key,
    name        text        not null,
    create_at   timestamp   not null,
    status      varchar     not null,
    user_id     bigint      not null references camunda_app.users(id)
    );
-- rollback DROP TABLE IF EXISTS camunda_app.tickets;

-- changeset Lakeev-DA:baseline-3
ALTER TABLE IF EXISTS camunda_app.users ADD COLUMN IF NOT EXISTS email text UNIQUE;
-- rollback ALTER TABLE IF EXISTS camunda_app.users DROP COLUMN IF EXISTS email;

-- changeset Lakeev-DA:baseline-4
CREATE INDEX idx_tickets_user_id ON camunda_app.tickets (user_id);
-- rollback DROP INDEX IF EXISTS camunda_app.idx_tickets_user_id;
