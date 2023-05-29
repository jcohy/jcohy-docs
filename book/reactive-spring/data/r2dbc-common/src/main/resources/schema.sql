create table if not exists  customer (
    id    serial  not null primary key,
    email varchar not null
);

truncate customer;