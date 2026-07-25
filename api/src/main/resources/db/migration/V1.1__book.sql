create table main.book (
                      publication_date date not null,
                      created_at timestamp not null,
                      rev_version bigint not null,
                      updated_at timestamp not null,
                      authorId varchar(36),
                      id varchar(36) not null,
                      category varchar(255) check ((category in ('DRAMA','MUSICAL','ACTION','DOCUMENTARY','ADVENTURE','THRILLER','COMEDY'))),
                      description varchar(255),
                      metadata clob,
                      title varchar(255) not null,
                      primary key (id)
);

create table main.book_audit (
                            REV integer not null,
                            REVTYPE tinyint,
                            publication_date date,
                            updated_at timestamp,
                            authorId varchar(36),
                            id varchar(36) not null,
                            category varchar(255) check ((category in ('DRAMA','MUSICAL','ACTION','DOCUMENTARY','ADVENTURE','THRILLER','COMEDY'))),
                            description varchar(255),
                            metadata clob,
                            title varchar(255),
                            primary key (REV, id)
);