create table USERS(
    username varchar(50) not null primary key,
    password varchar(500) not null,
    email varchar(100) not null,
    enabled boolean not null
);

create table AUTHORITIES (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

-- user upassword
insert into USERS values('user', '$2a$10$BqiDdEEWqL1dFP1q/BnWS.q2XBaue.FUOOfV4hdLZztbGaNduJseq', 'user@mail.ru', true);
-- user1 upassword1
insert into USERS values('user1', '$2a$10$MAiErMLNvakEjFJX0LkIfOkw1fr8DfBPOk1mkSJDKz.hxl9N9dAFS', 'user1@mail.ru', true);
-- manager mpassword
insert into USERS values('manager', '$2a$10$JmNtCdTa63vQHXDB2gvTTO752rUiFvBJqTzs3zL9k5Re/SxVlJ1hC', 'manager@mail.ru', true);
-- admin  apassword
insert into USERS values('admin', '$2a$10$yMK04o.HsK9N2Xv.ENsrDe3HiXUqZhoeQxYqMum8oHzL.U5uglREm', 'admin@mail.ru', true);

-- Роли:
--  USER обычный пользоватьель, может регистрироватьс и создавать объявления
--  MANAGER пользоватьель, может модерировать объявляения, т.е. разрешать их публикацию
--  ADMIN может назначать роли другим пользователям
insert into AUTHORITIES values('user', 'USER');
insert into AUTHORITIES values('user1', 'USER');
insert into AUTHORITIES values('manager', 'MANAGER');
insert into AUTHORITIES values('admin', 'ADMIN');


create table TYPE_OF_ANIMAL  (
            id    long not null primary key,
            name  VARCHAR(100) NOT NULL
);

INSERT INTO TYPE_OF_ANIMAL (id, name)
VALUES
    (1, 'Cat'),
    (2, 'Dog');


CREATE TABLE animals (
    id                      bigint PRIMARY KEY,
    name                    VARCHAR(40) NOT NULL,
    gender                  VARCHAR(10) NOT NULL,
    age                     INT NOT NULL,
    condition               VARCHAR(255) NOT NULL,
    description             VARCHAR(255) NOT NULL,
    username                varchar(50) not null,
    typeOfAnimalId          int,
    constraint fk_user_animals foreign key(username) references users(username),
    constraint fk_typeOfAnimal foreign key(typeOfAnimalId) references TYPE_OF_ANIMAL(id)
);

INSERT INTO animals ( id, name, gender, age, condition, description, username, typeOfAnimalId)
VALUES
( 1, 'Felix', 'Male', 5, 'Good', 'Looking for a host', 'user', 1),
( 2, 'Kassandra', 'Female', 4, 'Good', 'Looking for a host', 'user1', 2),
( 3, 'Rex', 'Male', 7, 'Good', 'Looking for a host', 'user1', 2);


CREATE TABLE photos (
                         id                      long auto_increment primary key ,
                         name                    varchar(255) not null ,
                         size                    long not null ,
                         keyPhoto                varchar(255) not null,
                         uploadDate              datetime,
                         comment                 varchar(255) not null ,
                         animalsId               bigint not null ,
                         constraint fk_photo_animals foreign key(animalsId) references animals(id)
);


