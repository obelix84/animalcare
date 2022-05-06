create table USERS(
    id bigint not null primary key auto_increment,
    username varchar(50) not null,
    password varchar(80) not null,
    email varchar(100) not null,
    enabled boolean not null
);
create unique index ix_users_username on users (username);

create table AUTHORITIES (
    id bigint not null primary key auto_increment,
    authority varchar(50) not null
);
create unique index ix_auth_authority on authorities (authority);

create table USERS_AUTHORITIES
(
    user_id bigint not null,
    authorities_id bigint not null,
    primary key (user_id, authorities_id),
    foreign key (user_id) references USERS (id),
    foreign key (authorities_id) references AUTHORITIES (id)
);

-- пароль везде одинаковый
-- admin password
-- insert into USERS(username, password, email, enabled)  values('admin', '$2a$10$yMK04o.HsK9N2Xv.ENsrDe3HiXUqZhoeQxYqMum8oHzL.U5uglREm', 'admin@mail.ru', true);
insert into USERS(username, password, email, enabled)  values('admin', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'admin@mail.ru', true);
-- user password
insert into USERS(username, password, email, enabled)  values('user', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'user@mail.ru', true);
-- user1 password
insert into USERS(username, password, email, enabled)  values('user1', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'user1@mail.ru', true);
-- manager password
insert into USERS(username, password, email, enabled) values('manager', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'manager@mail.ru', true);

CREATE TABLE animals (
    id                      bigserial PRIMARY KEY,
    kind                    VARCHAR(80) NOT NULL,
    name                    VARCHAR(40) NOT NULL,
    gender                  VARCHAR(10) NOT NULL,
    age                     INT NOT NULL,
    condition               VARCHAR(255) NOT NULL,
    description             CHARACTER VARYING
);

INSERT INTO animals (kind, name, gender, age, condition, description)
VALUES
('Cat', 'Felix', 'Male', 5, 'Good', 'Looking for a host'),
('Cat', 'Kassandra', 'Female', 4, 'Good', 'Looking for a host'),
('Dog', 'Rex', 'Male', 7, 'Good', 'Looking for a host');

-- Роли:
--  USER обычный пользоватьель, может регистрироватьс и создавать объявления
--  MANAGER пользоватьель, может модерировать объявляения, т.е. разрешать их публикацию
--  ADMIN может назначать роли другим пользователям
insert into AUTHORITIES (authority) values('ADMIN');
insert into AUTHORITIES (authority) values('USER');
insert into AUTHORITIES (authority) values('MANAGER');

insert into USERS_AUTHORITIES values(1, 1);
insert into USERS_AUTHORITIES values(1, 2);
insert into USERS_AUTHORITIES values(1, 3);
insert into USERS_AUTHORITIES values(2, 2);
insert into USERS_AUTHORITIES values(3, 2);
insert into USERS_AUTHORITIES values(4, 3);

