
-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence hibernate_sequence start with 1 increment by 1;

create table otus_user
(
    id   bigint not null primary key,
    name varchar(50),
    login varchar(50),
    password varchar(50)
);

insert into otus_user(name, login, password) values('User1', 'user1', 'user1');
insert into otus_user(name, login, password) values('User2', 'user2', 'user2');
insert into otus_user(name, login, password) values('User3', 'user3', 'user3');
insert into otus_user(name, login, password) values('User3', 'user3', 'user3');
