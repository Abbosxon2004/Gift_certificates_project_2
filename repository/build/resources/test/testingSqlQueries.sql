/**
  I wrote these sql queries for testing functions
 */

drop table if exists certificate_tag;
drop table if exists gift_certificate;
drop table if exists tag;

create table gift_certificate
(
    id                integer primary key generated always as identity,
    name              varchar          not null,
    description       varchar          not null,
    price             double precision not null,
    duration          integer          not null,
    created_date      timestamp,
    last_updated_date timestamp
);

create table tag
(
    id   integer primary key generated always as identity,
    name varchar unique not null
);

create table certificate_tag
(
    certificate_id integer not null references gift_certificate (id) on update cascade on delete cascade,
    tag_id         integer not null references tag (id) on update cascade on delete cascade
);

insert into gift_certificate (name, description, price, duration, created_date, last_updated_date)
values ('certificate1', 'desciption1', 0.0, 0, '2000-01-01 01:00:00', '2000-01-01 01:00:00');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date)
values ('certificate2', 'desciption2', 0.0, 0, '2000-01-01 01:00:00', '2000-01-01 01:00:00');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date)
values ('certificate3', 'desciption3', 30.0, 50, '2000-01-01 01:00:00', '2000-01-01 01:00:00');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date)
values ('certificate4', 'desciption4', 35.0, 40, '2000-01-01 01:00:00', '2000-01-01 01:00:00');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date)
values ('certificate5', 'desciption5', 20.0, 40, '2000-01-01 01:00:00', '2000-01-01 01:00:00');
insert into gift_certificate (name, description, price, duration, created_date, last_updated_date)
values ('certificate6', 'desciption6', 10.0, 10, '2000-01-01 01:00:00', '2000-01-01 01:00:00');

insert into tag (name)
values ('tag1');
insert into tag (name)
values ('tag2');
insert into tag (name)
values ('tag3');
insert into tag (name)
values ('tag4');
insert into tag (name)
values ('tag5');
insert into tag (name)
values ('tag6');


insert into certificate_tag (certificate_id, tag_id)
values (1, 1);
insert into certificate_tag (certificate_id, tag_id)
values (2, 2);
insert into certificate_tag (certificate_id, tag_id)
values (3, 3);
insert into certificate_tag (certificate_id, tag_id)
values (4, 4);
insert into certificate_tag (certificate_id, tag_id)
values (5, 5);
insert into certificate_tag (certificate_id, tag_id)
values (6, 6);
