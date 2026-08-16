create unlogged table staging
(
    type    text NOT NULL,
    key     text PRIMARY KEY,
    revision integer NOT NULL DEFAULT 0,
    last_modified text NOT NULL,
    json JSONB NOT NULL,
    processed boolean NOT NULL DEFAULT false
);