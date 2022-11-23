create sequence if not exists role_id_seq start 1;

create table roles (
                       id   BIGSERIAL PRIMARY KEY ,
                       name VARCHAR(50) NOT NULL
);

create table users_roles (
                             user_id BIGINT NOT NULL references users(id),
                             roles_id BIGINT NOT NULL references roles(id)
);

insert into roles (name) VALUES ('ROLE_ADMIN');
insert into roles (name) VALUES ('ROLE_USER');