-- liquibase formatted sql

-- changeset demaz:1
create table if not exists users(
    id                  int generated by default as identity primary key,
    email               varchar,
    first_name          varchar,
    last_name           varchar,
    phone               varchar,
    image               varchar,
    password            varchar,
    role                int
);