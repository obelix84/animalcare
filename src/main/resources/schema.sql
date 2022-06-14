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
VALUES ('Мальчик'),
       ('Девочка');

CREATE TABLE ANIMAL_PHOTOS
(
    id          bigint auto_increment primary key,
    name        varchar(255) not null,
    size        bigint       not null,
    upload_date datetime,
    comment     varchar(255) not null
);

INSERT INTO ANIMAL_PHOTOS (name, size, upload_date, comment)
VALUES ('no_photo.jpg', 3515, '2022-05-28 12:00:00', 'image/jpeg'),
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
       ('015.jpg', 223452, '2022-05-28 12:00:00', 'image/jpeg'),
       ('016.jpg', 279005 , '2022-05-28 12:00:00', 'image/jpeg'),
       ('017.jpg', 265245, '2022-05-28 12:00:00', 'image/jpeg'),
       ('018.jpg', 209867, '2022-05-28 12:00:00', 'image/jpeg'),
       ('019.jpg', 590336, '2022-05-28 12:00:00', 'image/jpeg'),
       ('020.jpg', 252409, '2022-05-28 12:00:00', 'image/jpeg'),
       ('021.jpg', 96456, '2022-05-28 12:00:00', 'image/jpeg'),
       ('022.jpg', 1196032, '2022-05-28 12:00:00', 'image/jpeg'),
       ('023.jpg', 188441, '2022-05-28 12:00:00', 'image/jpeg'),
       ('027.jpg', 655073, '2022-05-28 12:00:00', 'image/jpeg'),
       ('025.jpg', 718748, '2022-05-28 12:00:00', 'image/jpeg'),
       ('026.jpg', 936596, '2022-05-28 12:00:00', 'image/jpeg');

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
VALUES ('Мартин', 1, 1, 'Он привит, кастрирован, с лотком все отлично.',
        'Ответственно и качественно намывает гостей, дабы пришли люди надежные - которые не будут терять, а будут заботиться и любить.',
        1, true, 2),
       ( 'Эми', 2, 1, 'Здорова, все обработки, привита',
        'Невероятно добрая и красивая собачка, послушная, знает некоторые команды, очень дружелюбная, ладит с другими собачками и кошками',
        2, true, 2),
       ('Соня', 2, 8, 'Здорова, все обработки, привита',
        'Сонечке очень нужен человек, она любит обнимашки, целовашки',
        1, true, 2),
       ('Вася', 1, 8, 'Кастрирован, привит, приучен к лотку.',
        ' хотя Вася немолод и не очень здоров, он верит, что и для него кто-то готовит лежаночку и баночку вкусняшек. Ведь он этого достоин.',
        1, true, 2),
       ('Касандра', 2, 1, 'Малыш очень ласковый и общительный, с удовольствием сидит на ручках и поет песенки.',
        'Ночью спит, днем играет, воспитанная, но и пошалить может...',
        1, true, 2),
       ('Джульетта', 2, 3, 'Собачка стерилизованная, привитая, обработана от паразитов.',
        'Солнечная, не проблемная, ласковая, контактная.',
        2, true, 2),
       ('Агаша', 2, 1, 'Привита, приучена к лотку, дружелюбна к другим кошачьим.',
        'Родилась в дикости, подрастала в клетке. Ненадолго обрела дом, но вернули (не из-за характера или каких-то недостатков Агаши, причины другие)...',
        1, true, 2),
       ('Лео', 1, 1, 'Приучен к трехразовому выгулу (по возрасту). Но может до прогулки не дотерпеть .',
        'Лео дружелюбен к собакам и людям,кушает сухой корм,легко обучаем,совершенно не комфликтный.',
        2, true, 2),
       ('Феликс', 1, 1, 'Ему около года, привит, кастрирован, знает выгул, велась работа с кинологом',
        'Срочно нужен дом парню, его выкинули хозяева в другом регионе, попал в отлов.t',
        2, true, 2),
       ('Лада', 2, 4, 'Собака идеальная. Туалет улица (двух разовый выгул)',
        'На диванах кроватях спать не привыкла, но любит мягкие лежаки. Питание натуралка, каша мясо овощи',
        2, true, 2),
       ('Панда', 1, 6, 'Кастрирован, привит, приучен к лотку.',
        'Он хороший кот, но оказалось, что любит шуметь по ночам и копать цветы, но новый хозяин все равно его любит',
        1, true, 2),
       ('Тришка', 1, 1, 'Кастрирован, привит, приучен к лотку.',
        'Тришка не агрессивный, но боится людей,его можно гладить во время кормления вкусняшкой.',
        1, true, 2),
       ('Тишка', 1, 3, 'Кастрирован, привит, приучен к лотку.',
        'Ищет дом, ему около трех лет, неручной, но с удовольствием гладится и мурчит, когда ложится рядом.',
        1, true, 2),
       ('Ливона ', 2, 6, 'Здорова, все обработки, привита',
        'Ливона хочет всем поднять настроение в воскрсное утро :)',
        1, true, 2),
       ('Габр', 1, 7, 'Кастрирован, привит', 'Мальчик очень тянется к людям... ',
        2, true, 2),
       ('Эшлли', 2, 5, 'Обработки - от блох, клещей, глистов. Прививки-пока нет.',
        'Характер - ласковая, добрая, игривая. Бывает с рычанием',
        2, true, 2),
       ('Джек', 1, 4, 'Здоров, привит, кастрирован',
        'Как только вы его увидите, он сразу покорит ваше сердце. В нем очень много нерастраченной любви, которую он подарит тому, кто будет его безмерно обожать и считать членом своей семьи.',
        2, true, 2),
       ( 'Тианка', 2, 2, 'здорова, стерилизована, ест сухой корм'
       , 'С радостью проводит время с человеком, а когда нет времени на нее, она, с не меньшей радостью, идет дарить свою любовь остальным собаками, ну или кто попадется на ее пути))))'
       , 1, true, 2),
       ('inTRex', 1, 7000, 'Excellent', 'Looking for a host', 1, false, 2),
       ('inRamon', 1, 17000, 'Excellent', 'Looking for a host', 1, false, 2),
       ('in1TRex', 1, 7000, 'Excellent', 'Looking for a host', 1, false, 2),
       ('in2Ramon', 1, 17000, 'Excellent', 'Looking for a host', 1, false, 2),
       ('in3TRex', 1, 7000, 'Excellent', 'Looking for a host', 1, false, 2),
       ('in3Ramon', 1, 17000, 'Excellent', 'Looking for a host', 1, false, 2);



CREATE TABLE ANIMALS_ANIMAL_PHOTOS
(
    animal_id       bigint not null,
    animal_photo_id bigint not null,
    primary key (animal_id, animal_photo_id),
    foreign key (animal_id) references ANIMALS (id),
    foreign key (animal_photo_id) references ANIMAL_PHOTOS (id)
);

INSERT INTO ANIMALS_ANIMAL_PHOTOS (animal_id, animal_photo_id)
VALUES (1, 27),
       (1, 25),
       (1, 26),
       (2, 6),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 9),
       (7, 7),
       (8, 20),
       (9, 21),
       (10, 22),
       (11, 8),
       (12, 10),
       (13, 11),
       (14, 12),
       (15, 24),
       (16, 1),
       (17, 1),
       (18, 19),
       (19, 18),
       (20, 17),
       (21, 16),
       (22, 15),
       (23, 14),
       (24, 13);


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