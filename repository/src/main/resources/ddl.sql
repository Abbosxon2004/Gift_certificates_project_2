drop table if exists certificate_tag;
drop table if exists gift_certificate;
drop table if exists tag;

create table gift_certificate
(
    id               integer primary key generated always as identity,
    name             varchar unique   not null,
    description      varchar          not null,
    price            double precision not null,
    duration         integer          not null,
    created_date      timestamp,
    last_updated_date timestamp
);

create table tag
(
    id   integer primary key generated always as identity,
    name varchar unique not null
);

/* many-to-many joining */
create table certificate_tag
(
    certificate_id integer not null references gift_certificate (id) on update cascade on delete cascade,
    tag_id         integer not null references tag (id) on update cascade on delete cascade
);