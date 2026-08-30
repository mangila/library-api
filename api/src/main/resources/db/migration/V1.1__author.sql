create table author
(
    id               uuid                        not null,
    open_library_key text                        not null,
    name             text,
    personal_name    text,
    birth_date       text,
    death_date       text,
    location         text,
    wikipedia        text,
    bio              text,
    alternate_names  jsonb,
    books            jsonb,
    links            jsonb,
    uris             jsonb,
    works            jsonb,
    created_at       timestamp(6) with time zone not null,
    primary key (id)
);