create table USERS
(
    id             bigint       not null primary key auto_increment,
    password       varchar(80)  not null,
    first_name     varchar(80)  not null,
    last_name      varchar(80)  not null,
    email          varchar(100) not null,
    enabled        boolean      not null,
    photo_id       bigint,
    contact_number varchar(13)  not null
    -- foreign key (id) references PHOTOS(id)
);
create unique index ix_users_username on users (email);

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
insert into USERS(first_name, last_name, password, email, enabled, contact_number)
values ('admin', 'admin', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'admin@mail.ru',
        true, '+79001234570');

insert into USERS(first_name, last_name, password, email, enabled, contact_number)
values ('Иван', 'Иванов', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'шмфт@mail.ru',
        true, '+79111234570');
-- user password
insert into USERS(first_name, last_name, password, email, enabled, contact_number)
values ('Хорн', 'Тревор', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'horn@yes.com', true,
        '+79001234569');
-- user1 password
insert into USERS(first_name, last_name, password, email, enabled, contact_number)
values ('user1', 'user1', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO', 'user1@mail.ru',
        true, '+79001234568');
-- manager password
insert into USERS(first_name, last_name, password, email, enabled, contact_number)
values ('manager', 'manager', '$2a$10$BoAjnAXDD9xiR34FPSTP2.BMu..hYqhymJp46K/7j9aRzGowlgpBO',
        'manager@mail.ru', true, '+79001234567');



create table ANIMAL_GENDER
(
    id   bigint primary key auto_increment,
    name VARCHAR(100) NOT NULL
);

INSERT INTO ANIMAL_GENDER (name)
VALUES ('Мужской'),
       ('Женский');

CREATE TABLE ANIMAL_PHOTOS
(
    id          bigint auto_increment primary key,
    name        varchar(255) not null,
    size        bigint       not null,
    upload_date datetime,
    comment     varchar(255) not null
);

INSERT INTO ANIMAL_PHOTOS (name, size, upload_date, comment)
VALUES
('no_photo.jpg', 3515, '2022-05-28 12:00:00', 'image/jpeg'),
('001.jpg', 224970, '2022-05-28 12:00:00', 'image/jpeg'),
('002.jpg', 279068, '2022-05-28 12:00:00', 'image/jpeg'),
('003.jpg', 299182, '2022-05-28 12:00:00', 'image/jpeg'),
('004.jpg', 297733, '2022-05-28 12:00:00', 'image/jpeg'),
('005.jpg', 6935, '2022-05-28 12:00:00', 'image/jpeg'),
('006.jpg', 195101, '2022-05-28 12:00:00', 'image/jpeg'),
('007.jpg', 242378, '2022-05-28 12:00:00', 'image/jpeg'),
('008.jpg', 5573, '2022-05-28 12:00:00', 'image/jpeg'),
('009.jpg', 13518, '2022-05-28 12:00:00', 'image/jpeg'),
('010.jpg', 7582, '2022-05-28 12:00:00', 'image/jpeg'),
('011.jpg', 23712, '2022-05-28 12:00:00', 'image/jpeg'),
('012.jpg', 14490, '2022-05-28 12:00:00', 'image/jpeg'),
('013.jpg', 22294, '2022-05-28 12:00:00', 'image/jpeg'),
('014.jpg', 52612, '2022-05-28 12:00:00', 'image/jpeg'),
('015.jpg', 223452, '2022-05-28 12:00:00', 'image/jpeg');

create table ANIMAL_TYPE
(
    id   bigint primary key auto_increment,
    name VARCHAR(100) NOT NULL
);

INSERT INTO ANIMAL_TYPE (name)
VALUES ('Кошка'),
       ('Собака');

CREATE TABLE ANIMALS
(
    id               bigserial PRIMARY KEY,
    name             VARCHAR(40)  NOT NULL,
    animal_gender_id bigint       NOT NULL,
    age              INT          NOT NULL,
    condition        VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    animal_type_id   bigint       NOT NULL,
    active           boolean      NOT NULL DEFAULT FALSE,
    user_id          bigint       NULL,
    FOREIGN KEY (animal_gender_id) REFERENCES ANIMAL_GENDER (id),
    FOREIGN KEY (animal_type_id) REFERENCES ANIMAL_TYPE (id)
--    FOREIGN KEY (user_id) REFERENCES USERS (id)
);

INSERT INTO ANIMALS (name, animal_gender_id, age, condition, description, animal_type_id, active, user_id)
VALUES
( 'Феликс', 1, 5, 'Хорошее', 'Нуждается в заботливом хозяине', 1, true, 2),
( 'Кассандра', 2, 4, 'Хорошее', 'Нуждается в заботливом хозяине', 1, true, 2),
( 'Рекс', 1, 7, 'Хорошее', 'Нуждается в заботливом хозяине', 2, true, 2),
( 'Август', 1, 5, 'Хорошее', 'Нуждается в заботливом хозяине', 1, true, 2),
( 'Ника', 2, 4, 'Хорошее', 'Нуждается в заботливом хозяине', 1, true, 2),
( 'Берг', 1, 7, 'Хорошее', 'Нуждается в заботливом хозяине', 2, true, 2),
( 'Вульф', 1, 5, 'Хорошее', 'Нуждается в заботливом хозяине', 1, true, 2),
( 'Адель', 2, 4, 'Хорошее', 'Нуждается в заботливом хозяине', 1, true, 2),
( 'Дантес', 1, 7, 'Хорошее', 'Нуждается в заботливом хозяине', 2, true, 2),
( 'Тираннозавр', 1, 7000, 'Превосходное', 'Нуждается в заботливом хозяине', 2, false, 2),
( 'Трицератопс', 1, 17000, 'Превосходное', 'Нуждается в заботливом хозяине', 2, false, 2),
( 'Стегозавр', 1, 7000, 'Превосходное', 'Нуждается в заботливом хозяине', 2, false, 2),
( 'Велоцираптор', 1, 17000, 'Превосходное', 'Нуждается в заботливом хозяине', 2, false, 2),
( 'Диплодок', 1, 7000, 'Превосходное', 'Нуждается в заботливом хозяине', 2, false, 2),
( 'Скутеллозавр', 1, 17000, 'Превосходное', 'Нуждается в заботливом хозяине', 2, false, 2);



CREATE TABLE ANIMALS_ANIMAL_PHOTOS
(
    animal_id       bigint not null,
    animal_photo_id bigint not null,
    primary key (animal_id, animal_photo_id),
    foreign key (animal_id) references ANIMALS (id),
    foreign key (animal_photo_id) references ANIMAL_PHOTOS (id)
);

INSERT INTO ANIMALS_ANIMAL_PHOTOS (animal_id, animal_photo_id)
VALUES (1, 2),
       (2, 3),
       (3, 1),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 1),
       (11, 10),
       (12, 11),
       (13, 12),
       (14, 13),
       (15, 14);


-- Роли:
--  USER обычный пользователь, может регистрироваться и создавать объявления
--  MANAGER пользоватьель, может модерировать объявляения, т.е. разрешать их публикацию
--  ADMIN может назначать роли другим пользователям
insert into AUTHORITIES (authority)
values ('USER');
insert into AUTHORITIES (authority)
values ('MANAGER');
insert into AUTHORITIES (authority)
values ('ADMIN');

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