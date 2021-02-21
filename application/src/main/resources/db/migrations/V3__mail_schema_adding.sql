CREATE SCHEMA IF NOT EXISTS mail_schema AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS mail_schema.mails
(
    id              serial
                        constraint mail_pkey
                        primary key,
    mail_content    varchar(255) not null
);