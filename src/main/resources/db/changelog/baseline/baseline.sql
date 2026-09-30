-- liquibase formatted sql

-- changeset Lakeev-DA:baseline-1
CREATE TABLE IF NOT EXISTS camunda_app.users
(
    id        bigserial primary key         not null,
    fio       text                          not null,
    birthday  date                          not null
);

-- changeset Lakeev-DA:baseline-2
CREATE TABLE IF NOT EXISTS camunda_app.tickets
(
    id        bigserial primary key          not null,
    name      text                           not null,
    create_at timestamp                      not null,
    status    varchar                        not null,
    user_id   bigint    references users(id) not null
);