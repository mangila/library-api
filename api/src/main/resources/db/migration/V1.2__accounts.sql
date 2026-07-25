create table accounts
(
    id         uuid,
    username   text unique                 not null,
    password   text                        not null,
    active     boolean                     not null,
    roles      jsonb                       not null,
    created_at timestamp(6) with time zone not null,
    updated_at timestamp(6) with time zone not null,
    version    bigint                      not null,
    PRIMARY KEY (id)
);

create table account_refresh_tokens
(
    id         uuid,
    account_id uuid,
    token      text unique,
    expires_at timestamp(6) with time zone not null,
    user_agent text                        not null,
    PRIMARY KEY (id)
);

create table account_settings
(
    account_id uuid,
    PRIMARY KEY (account_id)
);
