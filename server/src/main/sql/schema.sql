CREATE TABLE user_role (
    role_name VARCHAR(20) PRIMARY KEY
);

INSERT INTO user_role (role_name)
VALUES ('user'),
       ('admin');

CREATE TABLE user (
    PRIMARY KEY (id),
    id        CHAR(36)     NOT NULL,
    username  VARCHAR(30)  NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    role_name VARCHAR(20)  NOT NULL,
    FOREIGN KEY (role_name) REFERENCES user_role (role_name)
);

CREATE TABLE category (
    PRIMARY KEY (id),
    id           CHAR(36)     NOT NULL,
    name         VARCHAR(30)  NOT NULL UNIQUE
);

CREATE TABLE channel (
    PRIMARY KEY (id),
    id            CHAR(36)     NOT NULL,
    title         VARCHAR(30)  NOT NULL,
    link          VARCHAR(255) NOT NULL UNIQUE,
    description   VARCHAR(255) NOT NULL,
    img_url       VARCHAR(255),
    category      VARCHAR(36) NOT NULL,
    FOREIGN KEY (category) REFERENCES category (id)
);
