create table if not exists role
(
    id  integer GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    name VARCHAR(10)
);
INSERT INTO role (name)
VALUES
    ('USER'),
    ('ADMIN');

create table if not exists image
(
    id  integer GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    user_default blob
);
INSERT INTO image (user_default)
VALUES
    (null),
    (null);

create table if not exists user
(
    id integer GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    first_name VARCHAR(255),
    last_name  VARCHAR(255) ,
    email      VARCHAR(255),
    password   VARCHAR(255),
    user_picture blob,
    status     VARCHAR(255)
);
INSERT INTO user (first_name,last_name,email,password,user_picture, status)
VALUES
    ('Daniel','Yockney','dyol@email.com','123', null, 'ACTIVE'),
    ('Joel','Sta','joel@email.com','123', null, 'ACTIVE'),
    ('Kairol','Sta','kil@email.com','123', null, 'INACTIVE');



CREATE TABLE IF NOT EXISTS user_roles (
    user_id INTEGER,
    role_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
INSERT INTO user_roles(user_id, role_id)
VALUES
    (1,2),
    (2,1),
    (3,1);