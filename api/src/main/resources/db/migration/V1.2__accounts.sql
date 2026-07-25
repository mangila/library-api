create table accounts
(
    id            uuid                        not null,
    username      text unique                 not null,
    password      text                        not null,
    active        boolean                     not null,
    roles         jsonb                       not null,
    refresh_token jsonb,
    created_at    timestamp(6) with time zone not null,
    updated_at    timestamp(6) with time zone not null,
    PRIMARY KEY (id)
);