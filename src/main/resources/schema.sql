create table USERS
(
    id         bigint       not null primary key auto_increment,
    username   varchar(50)  not null,
    password   varchar(80)  not null,
    first_name varchar(80)  not null,
    last_name  varchar(80)  not null,
    email      varchar(100) not null,
    enabled    boolean      not null,
    photo_id   bigint
    -- foreign key (id) references PHOTOS(id)
);
create unique index ix_users_username on users (username);

create table AUTHORITIES
(
    id        bigint      not null primary key auto_increment,
    authority varchar(50) not null
);
create unique index ix_auth_authority on authorities (authority);

create table USERS_AUTHORITIES
(
    user_id        bigint not null,
    authorities_id bigint not null,
    primary key (user_id, authorities_id),
    foreign key (user_id) references USERS (id),
    foreign key (authorities_id) references AUTHORITIES (id)
);

-- пароль везде одинаковый
-- admin password
-- insert into USERS(username, password, email, enabled)  values('admin', '$2a$10$yMK04o.HsK9N2Xv.ENsrDe3HiXUqZhoeQxYqMum8oHzL.U5uglREm', 'admin@mail.ru', true);
insert into USERS(first_name, last_name, username, password, email, enabled)
values ('admin', 'admin', 'admin', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'admin@mail.ru',
        true);
-- user password
insert into USERS(first_name, last_name, username, password, email, enabled)
values ('Иванов', 'Иван', 'user', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'user@mail.ru', true);
-- user1 password
insert into USERS(first_name, last_name, username, password, email, enabled)
values ('user1', 'user1', 'user1', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'user1@mail.ru',
        true);
-- manager password
insert into USERS(first_name, last_name, username, password, email, enabled)
values ('manager', 'manager', 'manager', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO',
        'manager@mail.ru', true);


create table ANIMAL_GENDER
(
    id   bigint primary key auto_increment,
    name VARCHAR(100) NOT NULL
);

INSERT INTO ANIMAL_GENDER (name)
VALUES
('Male'),
('Female');

CREATE TABLE ANIMAL_PHOTOS
(
    id              bigint auto_increment primary key,
    name            varchar(255) not null,
    size            bigint       not null,
--    key_photo       varchar(255) not null,
    upload_date     datetime,
    comment         varchar(255) not null
);

INSERT INTO ANIMAL_PHOTOS (name, size, upload_date, comment)
VALUES
('no_photo.jpg', 3515, '2022-05-28 12:00:00', 'image/jpeg');

create table ANIMAL_TYPE
(
    id   bigint primary key auto_increment,
    name VARCHAR(100) NOT NULL
);

INSERT INTO ANIMAL_TYPE (name)
VALUES
('Cat'),
('Dog');

--CREATE TABLE animals
--(
--    id                  bigserial PRIMARY KEY,
--    name                VARCHAR(40)  NOT NULL,
--    gender              VARCHAR(10)  NOT NULL,
--    age                 INT          NOT NULL,
--    condition           VARCHAR(255) NOT NULL,
--    description         VARCHAR(255) NOT NULL,
--    user_id             bigint       not null,
--    type_of_animal_id   bigint       not null,
--    foreign key (user_id) references users (id),
--    foreign key (type_of_animal_id) references TYPE_OF_ANIMAL (id)
--);

CREATE TABLE ANIMALS
(
    id                  bigserial PRIMARY KEY,
    name                VARCHAR(40)  NOT NULL,
    animal_gender_id    bigint       NOT NULL,
    age                 INT          NOT NULL,
    condition           VARCHAR(255) NOT NULL,
    description         VARCHAR(255) NOT NULL,
    animal_type_id      bigint       NOT NULL,
    active              boolean      NOT NULL DEFAULT FALSE,
    user_id             bigint       NULL,
--    исправить на FOREIGN KEY
    FOREIGN KEY (animal_gender_id) REFERENCES ANIMAL_GENDER (id),
    FOREIGN KEY (animal_type_id) REFERENCES ANIMAL_TYPE (id)
--    FOREIGN KEY (user_id) REFERENCES USERS (id)
);

INSERT INTO ANIMALS (name, animal_gender_id, age, condition, description, animal_type_id, active, user_id)
VALUES
( 'Felix', 1, 5, 'Good', 'Looking for a host', 1, true, 2),
( 'Kassandra', 2, 4, 'Good', 'Looking for a host', true, 2, 2),
( 'Rex', 1, 7, 'Good', 'Looking for a host', 2, true, 2),
( '1Felix', 1, 5, 'Good', 'Looking for a host', 1, true, 2),
( '1Kassandra', 2, 4, 'Good', 'Looking for a host', true, 2, 2),
( '1Rex', 1, 7, 'Good', 'Looking for a host', 2, true, 2),
( '2Felix', 1, 5, 'Good', 'Looking for a host', 1, true, 2),
( '2Kassandra', 2, 4, 'Good', 'Looking for a host', true, 2, 2),
( '2Rex', 1, 7, 'Good', 'Looking for a host', 2, true, 2),
( 'inTRex', 1, 7000, 'Excellent', 'Looking for a host', 2, false , 2),
( 'inRamon', 1, 17000, 'Excellent', 'Looking for a host', 2, false , 2),
( 'in1TRex', 1, 7000, 'Excellent', 'Looking for a host', 2, false , 2),
( 'in2Ramon', 1, 17000, 'Excellent', 'Looking for a host', 2, false , 2),
( 'in3TRex', 1, 7000, 'Excellent', 'Looking for a host', 2, false , 2),
( 'in3Ramon', 1, 17000, 'Excellent', 'Looking for a host', 2, false , 2);


CREATE TABLE ANIMALS_ANIMAL_PHOTOS
(
    animal_id           bigint not null,
    animal_photo_id     bigint not null,
    primary key (animal_id, animal_photo_id),
    foreign key (animal_id) references ANIMALS (id),
    foreign key (animal_photo_id) references ANIMAL_PHOTOS (id)
);

INSERT INTO ANIMALS_ANIMAL_PHOTOS (animal_id, animal_photo_id)
VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1);

-- Роли:
--  USER обычный пользователь, может регистрироваться и создавать объявления
--  MANAGER пользоватьель, может модерировать объявляения, т.е. разрешать их публикацию
--  ADMIN может назначать роли другим пользователям
insert into AUTHORITIES (authority)
values ('ADMIN');
insert into AUTHORITIES (authority)
values ('USER');
insert into AUTHORITIES (authority)
values ('MANAGER');

insert into USERS_AUTHORITIES
values (1, 1);
insert into USERS_AUTHORITIES
values (1, 2);
insert into USERS_AUTHORITIES
values (1, 3);
insert into USERS_AUTHORITIES
values (2, 2);
insert into USERS_AUTHORITIES
values (3, 2);
insert into USERS_AUTHORITIES
values (4, 3);


CREATE TABLE photos
(
    id         bigint auto_increment primary key,
    name       varchar(255) not null,
    size       bigint       not null,
    keyPhoto   varchar(255) not null,
    uploadDate datetime,
    comment    varchar(255) not null
--    animalsId  bigint       not null,
--    constraint fk_photo_animals foreign key (animalsId) references animals (id)
);