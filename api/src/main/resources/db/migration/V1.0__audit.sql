create table public.REVINFO (
                         REV integer,
                         REVTSTMP bigint,
                         primary key (REV)
);

create sequence public.REVINFO_SEQ start with 1 increment by 50;