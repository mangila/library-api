create table main.author (
                        created_at timestamp not null,
                        rev_version bigint not null,
                        updated_at timestamp not null,
                        id varchar(36) not null,
                        books clob,
                        name varchar(255) not null,
                        primary key (id)
);

create table main.author_audit (
                              REV integer not null,
                              REVTYPE tinyint,
                              updated_at timestamp,
                              id varchar(36) not null,
                              books clob,
                              name varchar(255),
                              primary key (REV, id)
);