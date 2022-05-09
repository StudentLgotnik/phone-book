CREATE SCHEMA IF NOT EXISTS phonebook_schema AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS phonebook_schema.area_codes
(
    id          integer not null
                    constraint area_codes_pkey
                    primary key,
    code        integer not null,
    country     varchar(80) not null
);

ALTER TABLE phonebook_schema.area_codes owner to postgres;

CREATE TABLE IF NOT EXISTS phonebook_schema.users
(
    id          serial not null
                    constraint users_pkey
                    primary key,
    login       varchar(255),
    email       varchar(255),
    phone       varchar(15),
    password    varchar(255)
);

ALTER TABLE phonebook_schema.users owner to postgres;

CREATE TABLE IF NOT EXISTS phonebook_schema.phone_books
(
    id              serial not null
                        constraint phone_books_pkey
                        primary key,
    user_id         integer not null
                        constraint fklxp5djgvdxlwwmiievhg9vg8x
                        references phonebook_schema.users,
    title           varchar(255),
    creation_date   timestamp,
    update_date     timestamp
);

ALTER TABLE phonebook_schema.phone_books owner to postgres;

CREATE TABLE IF NOT EXISTS phonebook_schema.persons
(
    id              serial not null
                        constraint persons_pkey
                        primary key,
    age             integer,
    name            varchar(255),
    second_name     varchar(255),
    birth_day       timestamp,
    phone_id        integer not null
                        constraint fkbynpo6m9a5e5ogwp3o3msuwpm
                        references phonebook_schema.phone_books
);

ALTER TABLE phonebook_schema.persons owner to postgres;

CREATE TABLE IF NOT EXISTS phonebook_schema.phones
(
    id                  serial not null
                            constraint phones_pkey
                            primary key,
    phone_number        varchar(9),
    operator_name       varchar(255),
    funds               double precision,
    registration_date   timestamp,
    activation_date     timestamp,
    country_code_id     integer
                            constraint fk5l1vv0uskatt001b4vi3jb9vq
                            references phonebook_schema.area_codes,
    person_id           integer not null
                            constraint fk6w0yc7gvd2b5naf33f4io0ikv
                            references phonebook_schema.persons
);

ALTER TABLE phonebook_schema.phones owner to postgres;


