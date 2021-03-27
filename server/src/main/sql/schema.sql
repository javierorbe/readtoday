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
    category_name   VARCHAR(30)  PRIMARY KEY
);

CREATE TABLE channel (
    PRIMARY KEY (id),
    id          CHAR(36)     NOT NULL,
    title       VARCHAR(30)  NOT NULL,
    link        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    img_url     VARCHAR(255)
);

CREATE TABLE channel_categories (
    channel_id  CHAR(36) NOT NULL,
    category_name CHAR(30) NOT NULL,
    FOREIGN KEY (channel_id)
        REFERENCES channel (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (category_name)
        REFERENCES category (category_name) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (channel_id, category_name)
);
