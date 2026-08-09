create table public.author (
                        created_at timestamp not null,
                        rev_version bigint not null,
                        updated_at timestamp not null,
                        id varchar(36) not null,
                        books jsonb,
                        name varchar(255) not null,
                        primary key (id)
);

create table public.author_audit (
                              REV integer not null,
                              REVTYPE smallint,
                              updated_at timestamp,
                              id varchar(36) not null,
                              books jsonb,
                              name varchar(255),
                              primary key (REV, id)
);